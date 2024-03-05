package store.mybooks.resource.book.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.mybooks.resource.book.dto.request.BookCreateRequest;
import store.mybooks.resource.book.dto.request.BookModifyRequest;
import store.mybooks.resource.book.dto.response.BookBriefResponse;
import store.mybooks.resource.book.dto.response.BookCreateResponse;
import store.mybooks.resource.book.dto.response.BookDetailResponse;
import store.mybooks.resource.book.dto.response.BookModifyResponse;
import store.mybooks.resource.book.entity.Book;
import store.mybooks.resource.book.exception.BookNotExistException;
import store.mybooks.resource.book.exception.IsbnAlreadyExistsException;
import store.mybooks.resource.book.mapper.BookMapper;
import store.mybooks.resource.book.repotisory.BookRepository;
import store.mybooks.resource.book_category.dto.request.BookCategoryCreateRequest;
import store.mybooks.resource.book_category.service.BookCategoryService;
import store.mybooks.resource.book_status.entity.BookStatus;
import store.mybooks.resource.book_status.exception.BookStatusNotExistException;
import store.mybooks.resource.book_status.respository.BookStatusRepository;
import store.mybooks.resource.book_tag.dto.request.BookTagCreateRequest;
import store.mybooks.resource.book_tag.service.BookTagService;
import store.mybooks.resource.image.dto.response.ImageRegisterResponse;
import store.mybooks.resource.image.service.ImageService;
import store.mybooks.resource.image_status.entity.ImageStatus;
import store.mybooks.resource.image_status.enumeration.ImageStatusEnum;
import store.mybooks.resource.image_status.exception.ImageStatusNotExistException;
import store.mybooks.resource.image_status.repository.ImageStatusRepository;
import store.mybooks.resource.publisher.entity.Publisher;
import store.mybooks.resource.publisher.exception.PublisherNotExistException;
import store.mybooks.resource.publisher.repository.PublisherRepository;

/**
 * packageName    : store.mybooks.resource.book.service <br/>
 * fileName       : BookService<br/>
 * author         : newjaehun <br/>
 * date           : 2/24/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2/24/24        newjaehun       최초 생성<br/>
 */
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;
    private final PublisherRepository publisherRepository;
    private final BookCategoryService bookCategoryService;
    private final BookTagService bookTagService;
    //    private final BookAuthorService bookAuthorService;
    private final BookMapper bookMapper;
    private final ImageService imageService;
    private final ImageStatusRepository imageStatusRepository;

    /**
     * methodName : getBookBriefInfo
     * author : newjaehun
     * description : 간략하게 전체 도서 정보 검색.
     *
     * @param pageable pageable
     * @return page
     */
    @Transactional(readOnly = true)
    public Page<BookBriefResponse> getBookBriefInfo(Pageable pageable) {
        return bookRepository.getBookBriefInfo(pageable);
    }

    /**
     * methodName : getBookDetailInfo
     * author : newjaehun
     * description : 특정 도서 정보 검색.
     *
     * @param bookId 검색할 도서 ID
     * @return book detail response
     */
    @Transactional(readOnly = true)
    public BookDetailResponse getBookDetailInfo(Long bookId) {
        if (bookRepository.existsById(bookId)) {
            throw new BookNotExistException(bookId);
        }
        return bookRepository.getBookDetailInfo(bookId);
    }

    /**
     * methodName : createBook
     * author : newjaehun
     * description : 도서 추가하는 메서드.
     *
     * @param createRequest 추가할 도서의 정보 포함
     * @return bookCreateResponse : 추가된 도서의 name 포함
     */
    @Transactional
    public BookCreateResponse createBook(BookCreateRequest createRequest, MultipartFile thumbnail,
                                         List<MultipartFile> content)
            throws IOException {
        BookStatus bookStatus = bookStatusRepository.findById(createRequest.getBookStatusId())
                .orElseThrow(() -> new BookStatusNotExistException(createRequest.getBookStatusId()));

        Publisher publisher =
                publisherRepository.findById(createRequest.getPublisherId())
                        .orElseThrow(() -> new PublisherNotExistException(createRequest.getPublisherId()));

        if (bookRepository.existsByIsbn(createRequest.getIsbn())) {
            throw new IsbnAlreadyExistsException(createRequest.getIsbn());
        }

        Book book =
                new Book(bookStatus, publisher, createRequest.getName(), createRequest.getIsbn(),
                        createRequest.getPublishDate(), createRequest.getPage(),
                        createRequest.getIndex(), createRequest.getContent(), createRequest.getOriginalCost(),
                        createRequest.getSaleCost(), createRequest.getOriginalCost() / createRequest.getSaleCost(),
                        createRequest.getStock(),
                        createRequest.getIsPacking());
        Book newBook = bookRepository.save(book);
        Long bookId = newBook.getId();
        bookCategoryService.createBookCategory(new BookCategoryCreateRequest(bookId, createRequest.getCategoryList()));
        if (createRequest.getTagList() != null) {
            bookTagService.createBookTag(new BookTagCreateRequest(bookId, createRequest.getTagList()));
        }
        List<ImageRegisterResponse> imageRegisterResponseList = new ArrayList<>();
        ImageStatus thumbnailEnum = imageStatusRepository.findById(ImageStatusEnum.THUMBNAIL.getName()).orElseThrow(
                () -> new ImageStatusNotExistException("해당 하는 id의 이미지 상태가 없습니다."));
        ImageRegisterResponse imageRegisterResponse = imageService.saveImage(thumbnailEnum, null, book, thumbnail);
        imageRegisterResponseList.add(imageRegisterResponse);

        ImageStatus contentEnum = imageStatusRepository.findById(ImageStatusEnum.CONTENT.getName())
                .orElseThrow(() -> new ImageStatusNotExistException("해당 하는 id의 이미지 상태가 없습니다."));
        for (MultipartFile file : content) {
            imageRegisterResponseList.add(imageService.saveImage(contentEnum, null, book, file));
        }
        //TODO bookResponse dto에 이미지 값들도 넣어야한다
        return bookMapper.createResponse(newBook);
    }


    /**
     * methodName : modifyBook
     * author : newjaehun
     * description : 도서 수정하는 메서드.
     *
     * @param bookId        수정할 도서 ID
     * @param modifyRequest 수정할 도서의 정보 포함
     * @return book modify response
     */
    @Transactional
    public BookModifyResponse modifyBook(Long bookId, BookModifyRequest modifyRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotExistException(bookId));

        BookStatus bookStatus = bookStatusRepository.findById(modifyRequest.getBookStatusId())
                .orElseThrow(() -> new BookStatusNotExistException(modifyRequest.getBookStatusId()));

        book.setModifyRequest(bookStatus, modifyRequest.getSaleCost(),
                book.getOriginalCost() / modifyRequest.getSaleCost(),
                modifyRequest.getStock(), modifyRequest.getIsPacking());
        return bookMapper.modifyResponse(book);
    }
}

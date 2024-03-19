package store.mybooks.resource.review.service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.mybooks.resource.book.exception.BookNotExistException;
import store.mybooks.resource.book.repotisory.BookRepository;
import store.mybooks.resource.image.repository.ImageRepository;
import store.mybooks.resource.image.service.ImageService;
import store.mybooks.resource.image_status.entity.ImageStatus;
import store.mybooks.resource.image_status.enumeration.ImageStatusEnum;
import store.mybooks.resource.image_status.exception.ImageStatusNotExistException;
import store.mybooks.resource.image_status.repository.ImageStatusRepository;
import store.mybooks.resource.orderdetail.entity.OrderDetail;
import store.mybooks.resource.review.dto.mapper.ReviewMapper;
import store.mybooks.resource.review.dto.reqeust.ReviewCreateRequest;
import store.mybooks.resource.review.dto.reqeust.ReviewModifyRequest;
import store.mybooks.resource.review.dto.response.ReviewCreateResponse;
import store.mybooks.resource.review.dto.response.ReviewDetailGetResponse;
import store.mybooks.resource.review.dto.response.ReviewGetResponse;
import store.mybooks.resource.review.dto.response.ReviewModifyResponse;
import store.mybooks.resource.review.entity.Review;
import store.mybooks.resource.review.exception.ReviewAlreadyExistException;
import store.mybooks.resource.review.exception.ReviewNotExistException;
import store.mybooks.resource.review.repository.ReviewRepository;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.user.exception.UserNotExistException;
import store.mybooks.resource.user.repository.UserRepository;

/**
 * packageName    : store.mybooks.resource.review.service<br>
 * fileName       : ReviewService<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final ImageStatusRepository imageStatusRepository;

    private final ImageService imageService;

    private final ReviewMapper reviewMapper;

    private final BookRepository bookRepository;

    private final ImageRepository imageRepository;

    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest createRequest, Long userId,
                                             MultipartFile image)
            throws IOException {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId));

        // todo id를 통해서 오더 디테일 찾아오기
        OrderDetail orderDetail = new OrderDetail();

        // 리뷰 이미 존재하는지 확인
        if (reviewRepository.existsByOrderDetailId(orderDetail.getId())) {
            throw new ReviewAlreadyExistException(createRequest.getOrderDetailId());
        }

        // todo orderDetail 을 넣어주기
        Review review = new Review(user, orderDetail, createRequest.getRate(), createRequest.getTitle(),
                createRequest.getContent());

//        Review resultReview = reviewRepository.save(review);


        // todo 이거 원래는 resultReview 로 해야 함
        Review mockReview = reviewRepository.findById(3L).get();

        if (Objects.nonNull(image)) {
            ImageStatus imageStatus = imageStatusRepository.findById(ImageStatusEnum.REVIEW.getName()).orElseThrow(
                    () -> new ImageStatusNotExistException("리뷰 이미지 상태 없음."));

            // todo 원래는 resultReview 로 해야 함
            imageService.saveImage(imageStatus, mockReview, null, image);
        }


        return reviewMapper.toReviewCreateResponse(mockReview);
    }

    @Transactional
    public ReviewModifyResponse modifyReview(Long userId, Long reviewId, ReviewModifyRequest modifyRequest) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotExistException(userId);
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException(reviewId));
        review.modifyReview(modifyRequest.getRate(), modifyRequest.getTitle(), modifyRequest.getContent());
        return reviewMapper.toReviewModifyResponse(review);
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotExistException(reviewId));
    }

    public ReviewGetResponse findReview(Long userId, Long reviewId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotExistException(userId);
        }

        Optional<ReviewGetResponse> response = reviewRepository.getReview(reviewId);

        if(response.isEmpty()){
            throw new ReviewNotExistException(reviewId);
        }

        return response.get();
    }

    public Page<ReviewGetResponse> findReviewByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotExistException(userId);
        }
        return reviewRepository.getReviewByUserId(userId, pageable);
    }

    public Page<ReviewDetailGetResponse> findReviewByBookId(Long bookId, Pageable pageable) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotExistException(bookId);
        }
        return reviewRepository.getReviewByBookId(bookId, pageable);
    }


}

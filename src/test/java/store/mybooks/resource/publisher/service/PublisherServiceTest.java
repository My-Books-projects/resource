package store.mybooks.resource.publisher.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import store.mybooks.resource.publisher.dto.request.PublisherCreateRequest;
import store.mybooks.resource.publisher.dto.request.PublisherModifyRequest;
import store.mybooks.resource.publisher.dto.response.PublisherCreateResponse;
import store.mybooks.resource.publisher.dto.response.PublisherDeleteResponse;
import store.mybooks.resource.publisher.dto.response.PublisherGetResponse;
import store.mybooks.resource.publisher.dto.response.PublisherModifyResponse;
import store.mybooks.resource.publisher.entity.Publisher;
import store.mybooks.resource.publisher.exception.PublisherAlreadyExistException;
import store.mybooks.resource.publisher.exception.PublisherNotExistException;
import store.mybooks.resource.publisher.mapper.PublisherMapper;
import store.mybooks.resource.publisher.repository.PublisherRepository;

/**
 * packageName    : store.mybooks.resource.publisher.service
 * fileName       : PublisherServiceTest
 * author         : newjaehun
 * date           : 2/18/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/18/24        newjaehun       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private PublisherMapper publisherMapper;

    @InjectMocks
    private PublisherService publisherService;

    private Publisher publisher;
    private static final Integer id = 1;
    private static final String name = "authorName1";

    @BeforeEach
    void setUp() {
        publisher = new Publisher(id, name, LocalDate.now());
    }

    @Test
    @DisplayName("전체 출판사 조회")
    void givenPublisherListAndPageable_whenFindAllPublishers_thenReturnPagePublishersGetResponseList() {
        Pageable pageable = PageRequest.of(0, 2);
        List<PublisherGetResponse> publisherGetResponseList = Arrays.asList(
                new PublisherGetResponse() {
                    @Override
                    public Integer getId() {
                        return id;
                    }

                    @Override
                    public String getName() {
                        return name;
                    }
                },
                new PublisherGetResponse() {
                    @Override
                    public Integer getId() {
                        return 2;
                    }

                    @Override
                    public String getName() {
                        return "publisher1";
                    }
                });

        Page<PublisherGetResponse> pageGetResponse =
                new PageImpl<>(publisherGetResponseList, pageable, publisherGetResponseList.size());
        when(publisherRepository.findAllBy(pageable)).thenReturn(pageGetResponse);

        assertThat(publisherService.getAllPublisher(pageable)).isEqualTo(pageGetResponse);

        verify(publisherRepository, times(1)).findAllBy(pageable);
    }


    @Test
    @DisplayName("출판사 등록")
    void givenPublisherCreateRequest_whenCreatePublisher_thenSavePublisherAndReturnPublisherCreateResponse() {
        PublisherCreateRequest request = new PublisherCreateRequest(name);
        PublisherCreateResponse response = new PublisherCreateResponse();
        response.setName(request.getName());


        given(publisherRepository.existsByName(request.getName())).willReturn(false);
        given(publisherRepository.save(any(Publisher.class))).willReturn(publisher);

        when(publisherMapper.createResponse(any(Publisher.class))).thenReturn(response);

        publisherService.createPublisher(request);

        verify(publisherRepository, times(1)).existsByName(request.getName());
        verify(publisherRepository, times(1)).save(any(Publisher.class));
        verify(publisherMapper, times(1)).createResponse(any(Publisher.class));

    }

    @Test
    @DisplayName("이미 존재하는 출판사 이름을 등록")
    void givenPublisherCreateRequest_whenAlreadyExistPublisherNameCreate_thenThrowPublisherAlreadyExistException() {
        PublisherCreateRequest request = new PublisherCreateRequest(name);
        PublisherCreateResponse response = new PublisherCreateResponse();
        response.setName(request.getName());

        given(publisherRepository.existsByName(request.getName())).willReturn(true);

        assertThrows(PublisherAlreadyExistException.class, () -> publisherService.createPublisher(request));

        verify(publisherRepository, times(1)).existsByName(request.getName());
        verify(publisherMapper, times(0)).createResponse(any(Publisher.class));

    }

    @Test
    @DisplayName("출판사 수정")
    void givenPublisherIdAndPublisherModifyRequest_whenModifyPublisher_thenModifyPublisherAndReturnPublisherModifyResponse() {
        String changeName = "publisherNameChange";
        PublisherModifyRequest modifyRequest = new PublisherModifyRequest(changeName);
        PublisherModifyResponse modifyResponse = new PublisherModifyResponse();
        modifyResponse.setName(modifyRequest.getChangeName());

        given(publisherRepository.findById(eq(id))).willReturn(Optional.of(publisher));

        given(publisherRepository.existsByName(modifyRequest.getChangeName())).willReturn(false);

        when(publisherMapper.modifyResponse(any(Publisher.class))).thenReturn(modifyResponse);

        publisherService.modifyPublisher(id, modifyRequest);

        verify(publisherRepository, times(1)).findById(eq(id));
        verify(publisherRepository, times(1)).existsByName(modifyRequest.getChangeName());
        verify(publisherMapper, times(1)).modifyResponse(any(Publisher.class));
    }

    @Test
    @DisplayName("존재하지 않는 출판사 수정")
    void givenPublisherId_whenNotExistPublisherModify_thenThrowPublisherNotExistException() {
        PublisherModifyRequest modifyRequest = new PublisherModifyRequest("publisherNameChange");

        given(publisherRepository.findById(eq(id))).willReturn(Optional.empty());

        assertThrows(PublisherNotExistException.class, () -> publisherService.modifyPublisher(id, modifyRequest));

        verify(publisherRepository, times(1)).findById(eq(id));
        verify(publisherRepository, times(0)).existsByName(modifyRequest.getChangeName());
        verify(publisherMapper, times(0)).modifyResponse(any(Publisher.class));
    }

    @Test
    @DisplayName("이미 존재하는 출판사 이름으로 수정")
    void givenPublisherIdAndPublisherModifyRequest_whenAlreadyExistPublisherNameModify_thenThrowPublisherAlreadyExistException() {
        PublisherModifyRequest modifyRequest = new PublisherModifyRequest("publisherNameChange");

        given(publisherRepository.findById(eq(id))).willReturn(Optional.of(publisher));

        when(publisherRepository.existsByName(modifyRequest.getChangeName())).thenReturn(true);

        assertThrows(PublisherAlreadyExistException.class, () -> publisherService.modifyPublisher(id, modifyRequest));

        verify(publisherRepository, times(1)).findById(eq(id));
        verify(publisherRepository, times(1)).existsByName(modifyRequest.getChangeName());
        verify(publisherMapper, times(0)).modifyResponse(any(Publisher.class));
    }


    @Test
    @DisplayName("출판사 삭제")
    void givenPublisherId_whenDeletePublisher_thenDeletePublisherAndReturnPublisherDeleteResponse() {
        given(publisherRepository.findById(eq(id))).willReturn(Optional.of(publisher));
        PublisherDeleteResponse deleteResponse = new PublisherDeleteResponse();
        deleteResponse.setName(name);
        doNothing().when(publisherRepository).deleteById(id);
        when(publisherMapper.deleteResponse(publisher)).thenReturn(deleteResponse);

       publisherService.deletePublisher(id);

        verify(publisherRepository, times(1)).findById(eq(id));
        verify(publisherRepository, times(1)).deleteById(id);
        verify(publisherMapper, times(1)).deleteResponse(any(Publisher.class));

    }

    @Test
    @DisplayName("존재하지 않는 출판사 삭제")
    void givenPublisherId_whenNotExistsPublisherDelete_thenThrowPublisherNotExistException() {
        given(publisherRepository.findById(eq(id))).willReturn(Optional.empty());

        assertThrows(PublisherNotExistException.class, () -> publisherService.deletePublisher(id));

        verify(publisherRepository, times(1)).findById(eq(id));
        verify(publisherRepository, times(0)).deleteById(id);
        verify(publisherMapper, times(0)).deleteResponse(any(Publisher.class));
    }
}
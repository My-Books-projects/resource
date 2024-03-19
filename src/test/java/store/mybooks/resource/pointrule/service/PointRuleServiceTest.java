package store.mybooks.resource.pointrule.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.util.ReflectionTestUtils;
import store.mybooks.resource.pointrule.dto.mapper.PointRuleMapper;
import store.mybooks.resource.pointrule.dto.request.PointRuleCreateRequest;
import store.mybooks.resource.pointrule.dto.request.PointRuleModifyRequest;
import store.mybooks.resource.pointrule.dto.response.PointRuleCreateResponse;
import store.mybooks.resource.pointrule.dto.response.PointRuleModifyResponse;
import store.mybooks.resource.pointrule.dto.response.PointRuleResponse;
import store.mybooks.resource.pointrule.entity.PointRule;
import store.mybooks.resource.pointrule.exception.PointRuleNotExistException;
import store.mybooks.resource.pointrule.repository.PointRuleRepository;
import store.mybooks.resource.pointrulename.entity.PointRuleName;
import store.mybooks.resource.pointrulename.exception.PointRuleNameNotExistException;
import store.mybooks.resource.pointrulename.repository.PointRuleNameRepository;

/**
 * packageName    : store.mybooks.resource.point_rule.service<br>
 * fileName       : PointRuleServiceTest<br>
 * author         : minsu11<br>
 * date           : 3/8/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/8/24        minsu11       최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class PointRuleServiceTest {
    @InjectMocks
    PointRuleService pointRuleService;
    @Mock
    PointRuleRepository pointRuleRepository;
    @Mock
    PointRuleNameRepository pointRuleNameRepository;


    @Mock
    PointRuleMapper pointRuleMapper;


    @Test
    @DisplayName("id로 포인트 규정 조회 성공 테스트")
    void givenId_whenGetPointRuleById_thenReturnPointRuleResponse() {
        PointRuleResponse expected = new PointRuleResponse(1, "test12", 10, null);
        when(pointRuleRepository.getPointRuleById(any())).thenReturn(Optional.of(expected));

        PointRuleResponse actual = pointRuleService.getPointRuleResponse(1);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getPointRuleName(), actual.getPointRuleName());
        Assertions.assertEquals(expected.getRate(), actual.getRate());
        Assertions.assertEquals(expected.getCost(), actual.getCost());
        verify(pointRuleRepository, times(1)).getPointRuleById(any());
    }

    @Test
    @DisplayName("id로 포인트 규정 조회 실패 테스트")
    void givenId_whenGetPointRuleById_thenThrowPointRuleNotExistException() {
        when(pointRuleRepository.getPointRuleById(any())).thenThrow(PointRuleNotExistException.class);

        Assertions.assertThrows(PointRuleNotExistException.class, () -> pointRuleService.getPointRuleResponse(1));
        verify(pointRuleRepository, times(1)).getPointRuleById(any());
    }

    @Test
    @DisplayName("전체 포인트 규정 조회 테스트")
    void whenGetPointRuleList_thenReturnPointRuleResponseList() {
        List<PointRuleResponse> expected = List.of(new PointRuleResponse(1, "test12", 10, null));
        when(pointRuleRepository.getPointRuleList()).thenReturn(expected);

        List<PointRuleResponse> actual = pointRuleService.getPointRuleList();

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.get(0).getRate(), actual.get(0).getRate());
        Assertions.assertEquals(expected.get(0).getCost(), actual.get(0).getCost());
        verify(pointRuleRepository, times(1)).getPointRuleList();
    }

    @Test
    @DisplayName("DB에 데이터가 없을 시 빈 리스트 반환 테스트")
    void whenGetPointRuleList_thenReturnEmptyList() {
        when(pointRuleRepository.getPointRuleList()).thenReturn(Collections.emptyList());
        List<PointRuleResponse> actual = pointRuleService.getPointRuleList();

        Assertions.assertEquals(0, actual.size());
        verify(pointRuleRepository, times(1)).getPointRuleList();
    }

    @Test
    @DisplayName("포인트 규정 전체 목록 페이징")
    void getPointRuleResponsePage() {
        List<PointRuleResponse> pointRuleResponseList = List.of(new PointRuleResponse(1, "test12", 10, null));
        Pageable pageable = PageRequest.of(0, 2);
        long total = pointRuleResponseList.size();
        Page<PointRuleResponse> expected = new PageImpl<>(pointRuleResponseList, pageable, total);

        when(pointRuleRepository.getPointRuleResponsePage(any())).thenReturn(expected);

        Page<PointRuleResponse> actual = pointRuleService.getPointRuleResponsePage(pageable);

        Assertions.assertEquals(expected.getContent(), actual.getContent());
        verify(pointRuleRepository, times(1)).getPointRuleResponsePage(any());

    }

    @Test
    @DisplayName("포인트 규정 등록 테스트")
    void givenPointRuleCreateRequest_whenSave_thenReturnPointRuleCreateResponse(@Mock PointRule pointRule, @Mock PointRuleName pointRuleName) {
        PointRuleCreateRequest request = new PointRuleCreateRequest();
        ReflectionTestUtils.setField(request, "pointRuleName", "test1");
        ReflectionTestUtils.setField(request, "rate", 10);
        ReflectionTestUtils.setField(request, "cost", null);
        PointRuleCreateResponse expected = new PointRuleCreateResponse("test1", 10, null);

        when(pointRuleNameRepository.findById(anyString())).thenReturn(Optional.of(pointRuleName));
        when(pointRuleRepository.findPointRuleByPointRuleName(anyString())).thenReturn(Optional.of(pointRule));
        when(pointRuleRepository.save(any(PointRule.class))).thenReturn(pointRule);
        when(pointRuleMapper.mapToPointRuleCreateResponse(any())).thenReturn(expected);

        PointRuleCreateResponse actual = pointRuleService.createPointRuleResponse(request);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected.getPointRuleName(), actual.getPointRuleName());
        Assertions.assertEquals(expected.getRate(), actual.getRate());
        Assertions.assertEquals(expected.getCost(), actual.getCost());

        verify(pointRuleNameRepository, times(1)).findById(anyString());
        verify(pointRuleRepository, times(1)).findPointRuleByPointRuleName(anyString());
        verify(pointRuleRepository, times(1)).save(any());
        verify(pointRuleMapper, times(1)).mapToPointRuleCreateResponse(any());
    }


    @Test
    @DisplayName("포인트 규정 명이 없어서 등록 실패 테스트")
    void givenPointRuleCreateRequest_whenSave_thenThrowPointRuleNameNotExistException() {
        PointRuleCreateRequest request = new PointRuleCreateRequest();
        ReflectionTestUtils.setField(request, "pointRuleName", "test2");
        ReflectionTestUtils.setField(request, "rate", 10);
        ReflectionTestUtils.setField(request, "cost", null);
        when(pointRuleNameRepository.findById(anyString())).thenThrow(PointRuleNameNotExistException.class);
        Assertions.assertThrows(PointRuleNameNotExistException.class, () -> pointRuleService.createPointRuleResponse(request));

        verify(pointRuleNameRepository, times(1)).findById(anyString());
        verify(pointRuleRepository, never()).findPointRuleByPointRuleName(anyString());
        verify(pointRuleRepository, never()).save(any(PointRule.class));
        verify(pointRuleMapper, never()).mapToPointRuleCreateResponse(any(PointRule.class));
    }

    @Test
    @DisplayName("포인트 규정 수정 테스트")
    void givenPointRuleModifyRequest_whenModifyPointRuleIsAvailable_thenReturnPointRuleModifyResponse(
            @Mock PointRule pointRule, @Mock PointRuleName pointRuleName
    ) {
        PointRuleModifyRequest request = new PointRuleModifyRequest();
        ReflectionTestUtils.setField(request, "pointRuleName", "test2");
        ReflectionTestUtils.setField(request, "rate", null);
        ReflectionTestUtils.setField(request, "cost", 10);
        PointRuleModifyResponse expected = new PointRuleModifyResponse("test1", null, 10);
        when(pointRuleRepository.findById(any())).thenReturn(Optional.of(pointRule));
        when(pointRuleNameRepository.findById(anyString())).thenReturn(Optional.of(pointRuleName));
        doNothing().when(pointRule).modifyPointRuleIsAvailable(any());
        when(pointRuleRepository.save(any(PointRule.class))).thenReturn(pointRule);
        when(pointRuleMapper.mapToPointRuleModifyResponse(any())).thenReturn(expected);


        PointRuleModifyResponse actual = pointRuleService.modifyPointRuleResponse(request, 1);
        Assertions.assertEquals(expected, actual);

        verify(pointRuleRepository, times(1)).findById(any());
        verify(pointRuleNameRepository, times(1)).findById(anyString());
        verify(pointRule, times(1)).modifyPointRuleIsAvailable(any());
        verify(pointRuleRepository, times(1)).save(any());
        verify(pointRuleMapper, times(1)).mapToPointRuleModifyResponse(any());
    }

    @Test
    @DisplayName("포인트 규정 수정 실패 테스트(수정 할 포인트 규정이 없을 때)")
    void givenPointRuleModifyRequest_whenFindById_thenThrowPointRuleNotExistException(@Mock PointRule pointRule, @Mock PointRuleModifyRequest request) {
        when(pointRuleRepository.findById(any())).thenThrow(PointRuleNotExistException.class);

        Assertions.assertThrows(PointRuleNotExistException.class, () -> pointRuleService.modifyPointRuleResponse(request, 1));

        verify(pointRuleRepository, times(1)).findById(any());
        verify(pointRuleNameRepository, never()).findById(anyString());
        verify(pointRule, never()).modifyPointRuleIsAvailable(any());
        verify(pointRuleRepository, never()).save(any());
        verify(pointRuleMapper, never()).mapToPointRuleModifyResponse(any());

    }

    @Test
    @DisplayName("포인트 규정 삭제 테스트")
    void whenModifyPointRuleIsAvailable(@Mock PointRule pointRule) {
        when(pointRuleRepository.findById(any())).thenReturn(Optional.of(pointRule));
        doNothing().when(pointRule).modifyPointRuleIsAvailable(any());
        Assertions.assertDoesNotThrow(() -> pointRuleService.deletePointRuleResponse(1));
        verify(pointRuleRepository, times(1)).findById(any());
        verify(pointRule, times(1)).modifyPointRuleIsAvailable(any());
    }

}
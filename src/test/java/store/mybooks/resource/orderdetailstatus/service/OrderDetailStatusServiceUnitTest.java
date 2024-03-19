package store.mybooks.resource.orderdetailstatus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import store.mybooks.resource.orderdetailstatus.dto.mapper.OrderDetailStatusMapper;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;
import store.mybooks.resource.orderdetailstatus.exception.OrderDetailStatusNotFoundException;
import store.mybooks.resource.orderdetailstatus.repository.OrderDetailStatusRepository;

/**
 * packageName    : store.mybooks.resource.order_detail_status.service<br>
 * fileName       : OrderDetailStatusServiceTest<br>
 * author         : minsu11<br>
 * date           : 2/20/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/20/24        minsu11       최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class OrderDetailStatusServiceUnitTest {

    @InjectMocks
    OrderDetailStatusService orderDetailStatusService;

    @Mock
    OrderDetailStatusRepository orderDetailStatusRepository;
    @Mock
    OrderDetailStatusMapper mapper;

    @Test
    @Order(1)
    @DisplayName("id 값에 대한 조회 테스트")
    void givenOrderDetailStatusId_whenFindById_thenReturnOrderDetailStatusResponse() {
        String orderDetailStatusId = "test";
        when(orderDetailStatusRepository.findById(orderDetailStatusId)).thenReturn(Optional.of(new OrderDetailStatus(orderDetailStatusId)));
        when(mapper.mapToOrderDetailStatusResponse(any())).thenReturn(new OrderDetailStatusResponse(orderDetailStatusId));

        OrderDetailStatus orderDetailStatus = orderDetailStatusRepository.findById(orderDetailStatusId).orElseThrow(OrderDetailStatusNotFoundException::new);
        OrderDetailStatusResponse actual = mapper.mapToOrderDetailStatusResponse(orderDetailStatus);
        OrderDetailStatusResponse expected = new OrderDetailStatusResponse(orderDetailStatusId);

        assertEquals(expected.getId(), actual.getId());

        verify(orderDetailStatusRepository, times(1)).findById(anyString());
        verify(mapper, times(1)).mapToOrderDetailStatusResponse(any());
    }

    @Test
    @Order(2)
    @DisplayName("id값과 동일하지 않은 경우에 OrderDetailStatusNotFoundException 테스트")
    void givenNotMatchOrderDetailStatusId_whenFindById_thenThrowsOrderDetailStatusNotFoundException() {
        when(orderDetailStatusRepository.findById(anyString())).thenThrow(OrderDetailStatusNotFoundException.class);
        assertThrows(OrderDetailStatusNotFoundException.class, () -> orderDetailStatusRepository.findById("test1"));
        verify(orderDetailStatusRepository, times(1)).findById(anyString());
    }

    @Test
    @Order(3)
    @DisplayName("상세 주문 상태에 대한 모든 데이터를 조회")
    void given_whenGetOrderDetailStatusList_thenReturnOrderDetailStatusResponseList() {
        List<OrderDetailStatusResponse> expected = List.of(new OrderDetailStatusResponse("test"));
        when(orderDetailStatusRepository.getOrderDetailStatusResponseList()).thenReturn(expected);

        List<OrderDetailStatusResponse> actual = orderDetailStatusRepository.getOrderDetailStatusResponseList();

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.size(), actual.size());
        verify(orderDetailStatusRepository, times(1)).getOrderDetailStatusResponseList();

    }

    @Test
    @Order(4)
    @DisplayName("상세 주문 상태에 대한 데이터가 없을 경우에 빈 리스트 조회")
    void given_whenGetOrderDetailStatusList_thenReturnEmptyList() {
        when(orderDetailStatusRepository.getOrderDetailStatusResponseList()).thenReturn(Collections.emptyList());

        List<OrderDetailStatusResponse> actual = orderDetailStatusRepository.getOrderDetailStatusResponseList();
        assertEquals(Collections.emptyList().toString(), actual.toString());
        assertEquals(Collections.emptyList().size(), actual.size());
        verify(orderDetailStatusRepository, times(1)).getOrderDetailStatusResponseList();

    }
}
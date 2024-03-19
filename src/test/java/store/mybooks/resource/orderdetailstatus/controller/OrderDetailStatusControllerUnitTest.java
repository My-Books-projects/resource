package store.mybooks.resource.orderdetailstatus.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.service.OrderDetailStatusService;

/**
 * packageName    : store.mybooks.resource.order_detail_status.controller<br>
 * fileName       : OrderDetailStatusControllerTest<br>
 * author         : minsu11<br>
 * date           : 2/20/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/20/24        minsu11       최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = OrderDetailStatusController.class)
class OrderDetailStatusControllerUnitTest {
    @Autowired
    MockMvc mockMvc;


    @MockBean
    OrderDetailStatusService orderDetailStatusService;


    @Test
    @Order(1)
    @DisplayName("api 경로로 들어온 id 값과 동일한 데이터를 DB에 찾아서 반환 테스트")
    void givenOrderDetailStatusId_whenGetOrderDetailStatusByOrderDetailStatusId_thenReturnOrderDetailStatusResponse()
            throws Exception {
        String orderDetailStatusId = "test";
        OrderDetailStatusResponse expected = new OrderDetailStatusResponse(orderDetailStatusId);
        when(orderDetailStatusService.getOrderDetailStatus(orderDetailStatusId)).thenReturn(expected);

        OrderDetailStatusResponse actual = new OrderDetailStatusResponse(orderDetailStatusId);

        mockMvc.perform(get("/api/order-detail-statuses/{id}", orderDetailStatusId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(actual.getId())));
        verify(orderDetailStatusService, times(1)).getOrderDetailStatus(anyString());
    }

    @Test
    @Order(2)
    @DisplayName("요청이 들어오면 상세 주문 상태의 모든 데이터를 리스트로 반환 테스트")
    void given_whenGetOrderDetailStatusList_thenReturnOrderDetailStatusResponseList() throws Exception {
        String expected = "test";
        String actual = "test";
        when(orderDetailStatusService.getOrderDetailStatusList()).thenReturn(
                List.of(new OrderDetailStatusResponse("test")));

        mockMvc.perform(get("/api/order-detail-statuses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo(actual)));
        verify(orderDetailStatusService, times(1)).getOrderDetailStatusList();

    }

    @Test
    @Order(3)
    @DisplayName("요청이 들어왔을 때 상세 주문 상태의 DB 테이블에 아무런 값이 없을 경우")
    void given_whenGetOrderDetailStatusList_thenReturnEmptyList() throws Exception {
        when(orderDetailStatusService.getOrderDetailStatusList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/order-detail-statuses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", equalTo(0)));
        verify(orderDetailStatusService, times(1)).getOrderDetailStatusList();
    }
}
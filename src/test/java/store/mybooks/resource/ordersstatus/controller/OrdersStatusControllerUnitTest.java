package store.mybooks.resource.ordersstatus.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import store.mybooks.resource.ordersstatus.dto.response.OrdersStatusResponse;
import store.mybooks.resource.ordersstatus.service.OrdersStatusService;

/**
 * packageName    : store.mybooks.resource.orders_status.controller
 * fileName       : OrdersStatusControllerTest
 * author         : minsu11
 * date           : 2/16/24
 * description    : OrdersStatus 관련 unit test code
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
class OrdersStatusControllerUnitTest {

    MockMvc mockMvc;

    @InjectMocks
    OrdersStatusController ordersStatusController;

    @Mock
    OrdersStatusService ordersStatusService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ordersStatusController).build();
    }

    @Test
    @Order(1)
    @DisplayName("id를 통한 api get 요청 시 id와 동일한 응답 테스트")
    void givenOrdersStatus_whenGetOrderStatusById_thenReturnOrdersStatusResponse() throws Exception {
        OrdersStatusResponse response = new OrdersStatusResponse("test");
        given(ordersStatusService.getOrdersStatusById(any())).willReturn(response);

        mockMvc.perform(get("/api/orders-statuses/{orderStatusId}", "test")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo("test")));
    }


    @Test
    @Order(2)
    @DisplayName("주문 상태 리스트를 요청 테스트")
    void givenOrdersStatus_whenGetOrdersStatusList_thenReturnOrdersStatusResponseList() throws Exception {
        given(ordersStatusService.getOrdersStatusList()).willReturn(
                List.of(new OrdersStatusResponse("test")));
        mockMvc.perform(get("/api/orders-statuses")
                ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo("test")));
    }

    @Test
    @Order(3)
    @DisplayName("DB table에 정보가 없는 경우 빈 리스트 반환하는 테스트")
    void givenOrdersStatus_whenGetOrdersStatusList_thenReturnEmptyOrdersStatusList() throws Exception {
        given(ordersStatusService.getOrdersStatusList()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders-statuses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }


}
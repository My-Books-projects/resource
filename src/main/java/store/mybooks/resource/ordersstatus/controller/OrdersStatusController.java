package store.mybooks.resource.ordersstatus.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.mybooks.resource.ordersstatus.dto.response.OrdersStatusResponse;
import store.mybooks.resource.ordersstatus.service.OrdersStatusService;

/**
 * packageName    : store.mybooks.resource.orders_status.controller
 * fileName       : OrdersStatusController
 * author         : minsu11
 * date           : 2/16/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
@RestController
@RequestMapping("/api/orders-statuses")
@RequiredArgsConstructor
public class OrdersStatusController {
    private final OrdersStatusService ordersStatusService;

    /**
     * methodName : getOrdersStatus<br>
     * author : minsu11<br>
     * description : 요청 들어온 id 값에 따른 주문 상태 응답<br>
     *
     * @param ordersStatusId 요청 들어오는 주문 상태 아이디<br>
     * @return 주문 상태 응답 타입에 대한 response entity 반환<br>
     */
    @GetMapping("/{ordersStatusId}")
    public ResponseEntity<OrdersStatusResponse> getOrdersStatus(
            @PathVariable(name = "ordersStatusId") String ordersStatusId
    ) {

        OrdersStatusResponse response = ordersStatusService.getOrdersStatusById(ordersStatusId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * methodName : getOrdersStatusList<br>
     * author : minsu11<br>
     * description : 요청에 따라 주문 상태의 모든 데이터를 리스트로 반환
     *
     * @return 주문 상태 응답 리스트 타입에 대한 response entity 반환<br>
     */
    @GetMapping
    public ResponseEntity<List<OrdersStatusResponse>> getOrdersStatusList(
    ) {
        List<OrdersStatusResponse> ordersStatusResponseList = ordersStatusService.getOrdersStatusList();
        return new ResponseEntity<>(ordersStatusResponseList, HttpStatus.OK);
    }


}

package store.mybooks.resource.orderdetailstatus.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.service.OrderDetailStatusService;

/**
 * packageName    : store.mybooks.resource.order_detail_status.controller
 * fileName       : OrderDetailStatusController
 * author         : minsu11
 * date           : 2/16/24
 * description    : 상세 주문 상태 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
@RestController
@RequestMapping("/api/order-detail-statuses")
@RequiredArgsConstructor
public class OrderDetailStatusController {
    private final OrderDetailStatusService orderDetailStatusService;


    /**
     * methodName : getOrderDetailStatusByOrderDetailStatusId
     * author : minsu11
     * description : id값에 따라 들어온 요청에 따라서 상세 주문 상태를 반환해줌
     *
     * @param id 경로에 지정된 아이디
     * @return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailStatusResponse> getOrderDetailStatusByOrderDetailStatusId(@PathVariable String id) {
        OrderDetailStatusResponse response = orderDetailStatusService.getOrderDetailStatus(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * methodName : getOrderDetailStatusList
     * author : minsu11
     * description : api 요청에 따라 상세 주문 상태 리스트를 반환해줌. <br>
     * 값이 없는 경우 빈 리스트가 반환되므로 주의 바람.
     *
     * @return response entity
     */
    @GetMapping
    public ResponseEntity<List<OrderDetailStatusResponse>> getOrderDetailStatusList() {
        List<OrderDetailStatusResponse> response = orderDetailStatusService.getOrderDetailStatusList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
package store.mybooks.resource.orderdetailstatus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.orderdetailstatus.dto.mapper.OrderDetailStatusMapper;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;
import store.mybooks.resource.orderdetailstatus.exception.OrderDetailStatusNotFoundException;
import store.mybooks.resource.orderdetailstatus.repository.OrderDetailStatusRepository;

/**
 * packageName    : store.mybooks.resource.order_detail_status.service <br>
 * fileName       : OrderDetailStatusService <br>
 * author         : minsu11 <br>
 * date           : 2/16/24 <br>
 * description    : 상세 주문 상태 데이터를 등록 및 조회를 할 수 있는 Service <br>
 * =========================================================== <br>
 * DATE              AUTHOR             NOTE <br>
 * ----------------------------------------------------------- <br>
 * 2/16/24        minsu11       최초 생성 <br>
 * 2/16/24        minsu11       최초 구현 <br>
 * 2/18/24        minsu11       데이터 베이스에서 반환된 데이터가 빈 값인 <br>
 * 경우 exception 반환에서 빈 리스트로 변경   <br>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailStatusService {
    private final OrderDetailStatusRepository orderDetailStatusRepository;
    private final OrderDetailStatusMapper mapper;


    /**
     * methodName : getOrderDetailStatus
     * author : minsu11
     * description : id와 동일한 상세 주문 상태 데이터를 가지고 옴
     *
     * @param id
     * @return order detail status response
     * @throws OrderDetailStatusNotFoundException id와 동일한 상세 주문 상태가 없는 경우
     */
    public OrderDetailStatusResponse getOrderDetailStatus(String id) {
        OrderDetailStatus orderDetailStatus = orderDetailStatusRepository.findById(id)
                .orElseThrow(() -> new OrderDetailStatusNotFoundException("상세 주문 상태 데이터 없음"));
        return mapper.mapToOrderDetailStatusResponse(orderDetailStatus);
    }


    /**
     * methodName : getOrderDetailStatusList
     * author : minsu11
     * description : 모든 상세 주문의 데이터를 가지고 옴.
     * 아무런 값이 없을 떈 빈 리스트를 반환을 해줌
     *
     * @return list
     */
    public List<OrderDetailStatusResponse> getOrderDetailStatusList() {

        return orderDetailStatusRepository.getOrderDetailStatusResponseList();
    }


}

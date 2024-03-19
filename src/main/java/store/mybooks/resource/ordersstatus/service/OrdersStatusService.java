package store.mybooks.resource.ordersstatus.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.ordersstatus.dto.mapper.OrdersStatusMapper;
import store.mybooks.resource.ordersstatus.dto.response.OrdersStatusResponse;
import store.mybooks.resource.ordersstatus.entity.OrdersStatus;
import store.mybooks.resource.ordersstatus.exception.OrdersStatusNotExistException;
import store.mybooks.resource.ordersstatus.repository.OrdersStatusRepository;

/**
 * packageName    : store.mybooks.resource.orders_status.service<br>
 * fileName       : OrdersStatusService<br>
 * author         : minsu11<br>
 * date           : 2/15/24<br>
 * description    : orders_status 테이블의 등록, 수정, 삭제 메서드를 담은 service<br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/15/24        minsu11       최초 생성<br>
 * 2/15/24        minsu11       최초 구현<br>
 * 2/18/24        minsu11       mapperStruct 사용으로 인한 코드 변경<br>
 * 2/19/24        minsu11       javadoc 최초 추가<br>
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersStatusService {
    private final OrdersStatusRepository ordersStatusRepository;
    private final OrdersStatusMapper mapper;

    /**
     * methodName : getOrdersStatusById<br>
     * author : minsu11<br>
     * description : id와 동일한 주문 상태 데이터를 가지고 옴<br>
     * <br>
     *
     * @param ordersStatusId <br>
     * @return orders status response<br>
     * @throws OrdersStatusNotExistException id와 동일한 주문 상태 데이터가 없는 경우<br>
     */
    public OrdersStatusResponse getOrdersStatusById(String ordersStatusId) {

        OrdersStatus ordersStatus = ordersStatusRepository.findById(
                ordersStatusId).orElseThrow(OrdersStatusNotExistException::new);
        return mapper.mapToResponse(ordersStatus);
    }

    /**
     * methodName : getOrdersStatusList<br>
     * author : minsu11<br>
     * description : 모든 주문 상태 데이터를 리스트로 가지고옴.<br>
     * 아무런 값이 없을 땐 빈 리스트를 반환 해줌.<br>
     * <br>
     *
     * @return list
     */
    public List<OrdersStatusResponse> getOrdersStatusList() {
        return ordersStatusRepository.getOrdersStatusList();
    }


}

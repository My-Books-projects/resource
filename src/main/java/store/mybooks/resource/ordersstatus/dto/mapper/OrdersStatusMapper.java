package store.mybooks.resource.ordersstatus.dto.mapper;

import org.mapstruct.Mapper;
import store.mybooks.resource.ordersstatus.dto.response.OrdersStatusResponse;
import store.mybooks.resource.ordersstatus.entity.OrdersStatus;

/**
 * packageName    : store.mybooks.resource.orders_status.dto.response
 * fileName       : OrdersStatusMapper
 * author         : minsu11
 * date           : 2/18/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/18/24        minsu11       최초 생성
 */
@Mapper(componentModel = "spring")
public interface OrdersStatusMapper {
    OrdersStatusResponse mapToResponse(OrdersStatus ordersStatus);

}

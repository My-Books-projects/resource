package store.mybooks.resource.orderdetailstatus.dto.mapper;

import org.mapstruct.Mapper;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;

/**
 * packageName    : store.mybooks.resource.order_detail_status.dto.response
 * fileName       : OrderDetailMapper
 * author         : minsu11
 * date           : 2/18/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/18/24        minsu11       최초 생성
 */
@Mapper(componentModel = "spring")
public interface OrderDetailStatusMapper {
    OrderDetailStatusResponse mapToOrderDetailStatusResponse(OrderDetailStatus orderDetailStatus);

}

package store.mybooks.resource.orderdetail.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailCreateResponse;
import store.mybooks.resource.orderdetail.entity.OrderDetail;

/**
 * packageName    : store.mybooks.resource.order_detail.dto.mapper<br>
 * fileName       : OrderDetailMapper<br>
 * author         : minsu11<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/17/24        minsu11       최초 생성<br>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderDetailMapper {
    @Mapping(source = "bookOrder.number", target = "orderNumber")
    @Mapping(source = "book.name", target = "bookName")
    @Mapping(source = "detailStatus.id", target = "detailStatus")
    OrderDetailCreateResponse mapToorderDetailCreateResponse(OrderDetail orderDetail);

}

package store.mybooks.resource.orderdetailstatus.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;

/**
 * packageName    : store.mybooks.resource.order_detail_status.repository
 * fileName       : OrderDetailStatusRepositoryCustom
 * author         : minsu11
 * date           : 2/16/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
@NoRepositoryBean
public interface OrderDetailStatusRepositoryCustom {
    List<OrderDetailStatusResponse> getOrderDetailStatusResponseList();
}

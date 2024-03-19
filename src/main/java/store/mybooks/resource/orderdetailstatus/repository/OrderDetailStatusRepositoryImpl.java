package store.mybooks.resource.orderdetailstatus.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.entity.QOrderDetailStatus;

/**
 * packageName    : store.mybooks.resource.order_detail_status.repository
 * fileName       : OrderDetailStatusRepositoryImpl
 * author         : minsu11
 * date           : 2/16/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
public class OrderDetailStatusRepositoryImpl extends QuerydslRepositorySupport implements OrderDetailStatusRepositoryCustom {
    public OrderDetailStatusRepositoryImpl() {
        super(OrderDetailStatusResponse.class);
    }

    @Override
    public List<OrderDetailStatusResponse> getOrderDetailStatusResponseList() {
        QOrderDetailStatus orderDetailStatus = QOrderDetailStatus.orderDetailStatus;
        return from(orderDetailStatus)
                .select(Projections.constructor(OrderDetailStatusResponse.class, orderDetailStatus.id))
                .fetch();
    }


}

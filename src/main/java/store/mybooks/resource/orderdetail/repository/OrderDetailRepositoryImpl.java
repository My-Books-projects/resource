package store.mybooks.resource.orderdetail.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.book.entity.QBook;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailInfoResponse;
import store.mybooks.resource.orderdetail.entity.OrderDetail;
import store.mybooks.resource.orderdetail.entity.QOrderDetail;

/**
 * packageName    : store.mybooks.resource.order_detail.repository<br>
 * fileName       : OrderDetailRespositoryImpl<br>
 * author         : minsu11<br>
 * date           : 3/18/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/18/24        minsu11       최초 생성<br>
 */
public class OrderDetailRepositoryImpl extends QuerydslRepositorySupport implements OrderDetailRepositoryCustom {
    private QOrderDetail orderDetail = QOrderDetail.orderDetail;


    public OrderDetailRepositoryImpl() {
        super(OrderDetail.class);
    }

    @Override
    public List<OrderDetailInfoResponse> getOrderDetailList(Long bookOrderId) {
        QBook book = QBook.book;
        return from(orderDetail)
                .leftJoin(orderDetail.book, book)
                .select(Projections.constructor(
                        OrderDetailInfoResponse.class,
                        orderDetail.book.name,
                        orderDetail.bookCost,
                        orderDetail.isCouponUsed
                ))
                .where(orderDetail.id.eq(bookOrderId))
                .fetch();
    }
}

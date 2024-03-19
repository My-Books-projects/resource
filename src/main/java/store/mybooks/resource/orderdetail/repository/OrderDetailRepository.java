package store.mybooks.resource.orderdetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mybooks.resource.orderdetail.entity.OrderDetail;

/**
 * packageName    : store.mybooks.resource.order_detail.repository<br>
 * fileName       : OrderDetailRepository<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailRepositoryCustom {
}

package store.mybooks.resource.ordersstatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mybooks.resource.ordersstatus.entity.OrdersStatus;

/**
 * packageName    : store.mybooks.resource.orders_status.repository
 * fileName       : OrderStatusRepository
 * author         : minsu11
 * date           : 2/15/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/15/24        minsu11       최초 생성
 */
public interface OrdersStatusRepository extends JpaRepository<OrdersStatus, String>, OrdersStatusRepositoryCustom {

}

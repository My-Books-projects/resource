package store.mybooks.resource.orderdetailstatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;

/**
 * packageName    : store.mybooks.resource.order_detail_status.repository
 * fileName       : OrderDetailStatusRepository
 * author         : minsu11
 * date           : 2/16/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
public interface OrderDetailStatusRepository extends JpaRepository<OrderDetailStatus, String>, OrderDetailStatusRepositoryCustom {
}

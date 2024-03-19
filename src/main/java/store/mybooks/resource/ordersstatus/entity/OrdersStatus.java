package store.mybooks.resource.ordersstatus.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.resource.ordersstatus.entity
 * fileName       : OrdersStatus
 * author         : minsu11
 * date           : 2/13/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/13/24        minsu11       최초 생성
 */
@Getter
@Entity
@Table(name = "orders_status")
@NoArgsConstructor
public class OrdersStatus {
    @Id
    @Column(name = "orders_status_id")
    private String id;

    @Column(name = "orders_status_created_date")
    private LocalDate createdDate;

    public OrdersStatus(String id) {
        this.id = id;
        this.createdDate = LocalDate.now();
    }
}

package store.mybooks.resource.orderdetailstatus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.resource.order_detail_status.entity
 * fileName       : OrderDetailStatus
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
@Table(name = "order_detail_status")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailStatus {

    @Id
    @Column(name = "order_detail_status_id")
    private String id;
}

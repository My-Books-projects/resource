package store.mybooks.resource.pointhistory.entity;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.pointrule.entity.PointRule;
import store.mybooks.resource.user.entity.User;

/**
 * packageName    : store.mybooks.resource.point_history.entity
 * fileName       : PointHistory
 * author         : minsu11
 * date           : 2/15/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/15/24        minsu11       최초 생성
 */
@Getter
@Entity
@Table(name = "point_history")
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Column(name = "point_status_cost")
    private Integer pointStatusCost;

    @Column(name = "point_history_created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private BookOrder bookOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rule_id")
    private PointRule pointRule;

    /**
     * 포인트 내역 생성자
     *
     * @param pointStatusCost the point status cost
     * @param user            the user
     * @param pointRule       the point rule
     * @param bookOrder       the book order
     */
    public PointHistory(Integer pointStatusCost, User user, PointRule pointRule, BookOrder bookOrder) {
        this.pointStatusCost = pointStatusCost;
        this.createdDate = LocalDate.now();
        this.user = user;
        this.pointRule = pointRule;
        this.bookOrder = bookOrder;
    }
}

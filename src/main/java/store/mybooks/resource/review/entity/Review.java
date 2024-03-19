package store.mybooks.resource.review.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.mybooks.resource.orderdetail.entity.OrderDetail;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.utils.TimeUtils;

/**
 * packageName    : store.mybooks.resource.review.entity
 * fileName       : Review
 * author         : masiljangajji
 * date           : 2/13/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/13/24        masiljangajji       최초 생성
 */

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    @Column(name = "review_rate")
    private Integer rate;

    @Column(name = "review_date")
    private LocalDate date;

    @Column(name = "review_title")
    private String title;

    @Column(name = "review_content")
    private String content;

    public Review(User user, OrderDetail orderDetail, Integer rate, String title, String content) {
        this.user = user;
        this.orderDetail = orderDetail;
        this.rate = rate;
        this.title = title;
        this.content = content;
        this.date = TimeUtils.nowDate();
    }

    public void modifyReview(Integer rate, String title, String content) {
        this.rate = rate;
        this.title = title;
        this.content = content;
    }

}

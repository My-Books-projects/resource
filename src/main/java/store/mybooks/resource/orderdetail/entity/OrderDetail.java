package store.mybooks.resource.orderdetail.entity;

import javax.persistence.*;
import lombok.*;
import store.mybooks.resource.book.entity.Book;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;
import store.mybooks.resource.usercoupon.entity.UserCoupon;
import store.mybooks.resource.wrap.entity.Wrap;

/**
 * packageName    : store.mybooks.resource.order_detail.entity
 * fileName       : OrderDetail
 * author         : minsu11
 * date           : 2/13/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/13/24        minsu11       최초 생성
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Column(name = "book_cost")
    private Integer bookCost;

    @Column(name = "order_detail_amount")
    private Integer amount;

    @Column(name = "is_coupon_used")
    private Boolean isCouponUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private BookOrder bookOrder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_status_id")
    private OrderDetailStatus detailStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_coupon_id")
    private UserCoupon userCoupon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrap_id")
    private Wrap wrap;


}

package store.mybooks.resource.bookorder.entity;

import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import store.mybooks.resource.delivery_rule.entity.DeliveryRule;
import store.mybooks.resource.ordersstatus.entity.OrdersStatus;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.usercoupon.entity.UserCoupon;


/**
 * packageName    : store.mybooks.resource.bookorder.entity
 * fileName       : BookOrder
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_order")
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_out_date")
    private LocalDate outDate;

    @CreatedDate
    @Column(name = "order_delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "order_date")
    private LocalDate date;

    @Column(name = "order_invoice_number")
    private String invoiceNumber;

    @Column(name = "order_receiver_name")
    private String receiverName;

    @Column(name = "order_receiver_address")
    private String receiverAddress;

    @Column(name = "order_receiver_phone_number")
    private String receiverPhoneNumber;

    @Column(name = "order_receiver_message")
    private String receiverMessage;

    @Column(name = "order_total_cost")
    private Integer totalCost;

    @Column(name = "order_point_cost")
    private Integer pointCost;

    @Column(name = "order_coupon_cost")
    private Integer couponCost;

    @Column(name = "is_coupon_used")
    private Boolean isCouponUsed;

    @Column(name = "order_number")
    private String number;

    @Column(name = "order_find_password")
    private String findPassword;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "orders_status_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrdersStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_rule_id")
    private DeliveryRule deliveryRule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_coupon_id")
    private UserCoupon userCoupon;


    public void modifyBookOrderAdmin(OrdersStatus ordersStatus) {
        this.orderStatus = ordersStatus;
        this.outDate = LocalDate.now();
    }

    public void registerBookOrderInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
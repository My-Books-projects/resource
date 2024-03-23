package store.mybooks.resource.orderdetail.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : store.mybooks.resource.order_detail.dto.response<br>
 * fileName       : OrderDetailInfoResponse<br>
 * author         : minsu11<br>
 * date           : 3/18/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/18/24        minsu11       최초 생성<br>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInfoResponse {
    private Long id;
    private String bookName;
    private Long couponId;
    private Integer cost;
    private Integer amount;
    private Boolean isCouponUsed;
    private String image;

    private String statusId;
    private Long orderDetailId;
}

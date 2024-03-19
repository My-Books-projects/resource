package store.mybooks.resource.orderdetail.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.resource.order_detail.dto.response<br>
 * fileName       : OrderDetailCreateResponse<br>
 * author         : minsu11<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/17/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class OrderDetailCreateResponse {
    private String bookName;
    private String orderNumber;
    private String detailStatus;
    private Boolean isCouponUsed;


}

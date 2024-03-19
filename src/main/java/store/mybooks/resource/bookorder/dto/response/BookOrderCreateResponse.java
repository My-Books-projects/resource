package store.mybooks.resource.bookorder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * packageName    : store.mybooks.resource.book_order.dto.response<br>
 * fileName       : BookOrderCreateResponse<br>
 * author         : minsu11<br>
 * date           : 3/2/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/2/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class BookOrderCreateResponse {
    private String orderStatus;
    private String number;
    private Integer totalCost;
    private Boolean isCouponUsed;

    public void updateIsCouponUsed(Boolean isCouponUsed) {
        this.isCouponUsed = isCouponUsed;
    }
}

package store.mybooks.resource.bookorder.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailInfoResponse;

/**
 * packageName    : store.mybooks.resource.bookorder.dto.response<br>
 * fileName       : BookOrderInfoPayReponse<br>
 * author         : minsu11<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/17/24        minsu11       최초 생성<br>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderInfoPayResponse {
    private String orderStatus;
    private String number;
    private Integer totalCost;
    private Boolean isCouponUsed;
    private List<OrderDetailInfoResponse> orderDetails;

    public BookOrderInfoPayResponse(String orderStatus, String number, Integer totalCost, Boolean isCouponUsed) {
        this.orderStatus = orderStatus;
        this.number = number;
        this.totalCost = totalCost;
        this.isCouponUsed = isCouponUsed;
    }

    public void updateOrderDetails(List<OrderDetailInfoResponse> orderDetails) {
        this.orderDetails = orderDetails;
    }

}

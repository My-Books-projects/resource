package store.mybooks.resource.bookorder.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailInfoResponse;


/**
 * packageName    : store.mybooks.resource.book_order.dto.response
 * fileName       : BookOrderResponse
 * author         : minsu11
 * date           : 2/15/24
 * description    : 회원아이디, 주문 상태 명, 배송 규정 아이디,
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/15/24        minsu11       최초 생성
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BookOrderUserResponse {
    private String statusId;
    private String deliveryRuleName;
    private Integer deliveryCost;
    private LocalDate orderDate;
    private String invoiceNumber;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhoneNumber;
    private String receiverMessage;
    private Integer totalCost;
    private Integer pointCost;
    private Integer couponCost;
    private String number;
    private String image;
    private List<OrderDetailInfoResponse> orderDetailInfoList;

    public void createOrderDetailInfos(List<OrderDetailInfoResponse> orderDetailInfoList) {
        this.orderDetailInfoList = orderDetailInfoList;
    }

    public BookOrderUserResponse(String statusId, String deliveryRuleName, Integer deliveryCost, LocalDate orderDate, String invoiceNumber, String receiverName, String receiverAddress, String receiverPhoneNumber, String receiverMessage, Integer totalCost, Integer pointCost, Integer couponCost, String number, String image) {
        this.statusId = statusId;
        this.deliveryRuleName = deliveryRuleName;
        this.deliveryCost = deliveryCost;
        this.orderDate = orderDate;
        this.invoiceNumber = invoiceNumber;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverMessage = receiverMessage;
        this.totalCost = totalCost;
        this.pointCost = pointCost;
        this.couponCost = couponCost;
        this.number = number;
        this.image = image;
    }
}

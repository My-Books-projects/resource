package store.mybooks.resource.bookorder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.resource.bookorder.dto.response<br>
 * fileName       : BookOrderPaymentInfoRespones<br>
 * author         : minsu11<br>
 * date           : 3/19/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/19/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class BookOrderPaymentInfoRespones {
    private String name;
    private String email;
    private String phoneNumber;
    private String orderNumber;
    private String orderName;
    private String orderStatus;

    public BookOrderPaymentInfoRespones(String name, String email, String phoneNumber, String orderNumber, String orderStatus) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
    }

    public void updateOrderName(String orderName) {
        this.orderName = orderName;

    }
}

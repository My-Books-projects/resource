package store.mybooks.resource.bookorder.dto.response.admin;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * packageName    : store.mybooks.resource.book_order.dto.response<br>
 * fileName       : BookOrderAdminResponse<br>
 * author         : minsu11<br>
 * date           : 3/2/24<br>
 * description    : 관리자가 보는 주문 내역 {@code response}.
 * 출고일이 되면 주문 상태를 변경 시켜줌
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/2/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class BookOrderAdminResponse {
    private Long id;
    private Long userId;
    private String statusId;
    private LocalDate date;
    private LocalDate outDate;
    private String invoiceNumber;
    private String number;
}

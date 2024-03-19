package store.mybooks.resource.bookorder.dto.response.admin;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * packageName    : store.mybooks.resource.book_order.dto.response<br>
 * fileName       : BookOrderModifyOrderStatusResponse<br>
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
public class BookOrderAdminModifyResponse {
    private Long id;
    private String statusId;
    private LocalDate outDate;
}

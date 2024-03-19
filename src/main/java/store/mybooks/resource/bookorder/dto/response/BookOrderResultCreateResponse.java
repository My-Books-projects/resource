package store.mybooks.resource.bookorder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.resource.bookorder.dto.response<br>
 * fileName       : BookOrderResultCreateResponse<br>
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
public class BookOrderResultCreateResponse {
    private BookOrderCreateResponse bookOrderInfo;
}

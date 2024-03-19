package store.mybooks.resource.bookorder.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.resource.bookorder.dto.request<br>
 * fileName       : BookOrderUserInfoRequest<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
@Getter
@NoArgsConstructor
public class BookOrderUserInfoRequest {
    private String userName;
    private String email;
    private String phoneNumber;
}

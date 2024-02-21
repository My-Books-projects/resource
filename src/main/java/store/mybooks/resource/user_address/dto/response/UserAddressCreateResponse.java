package store.mybooks.resource.user_address.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : store.mybooks.resource.user_address.dto.response
 * fileName       : UserAddressCreateResponse
 * author         : masiljangajji
 * date           : 2/13/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/13/24        masiljangajji       최초 생성
 */

@Getter
@Setter
@AllArgsConstructor
public class UserAddressCreateResponse {

    private String alias;
    private String roadName;
    private String detail;
    private Integer number;
    private String reference;

}

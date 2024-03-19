package store.mybooks.resource.pointhistory.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.resource.pointhistory.dto.request<br>
 * fileName       : PointHistoryRequest<br>
 * author         : minsu11<br>
 * date           : 3/19/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/19/24        minsu11       최초 생성<br>
 */
@Getter
@NoArgsConstructor
public class PointHistoryCreateRequest {
    private String orderNumber;
    private String pointName;
    private Integer pointCost;


}

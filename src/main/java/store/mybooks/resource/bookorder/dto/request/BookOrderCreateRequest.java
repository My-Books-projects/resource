package store.mybooks.resource.bookorder.dto.request;

import java.util.List;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : store.mybooks.resource.book_order.dto.request
 * fileName       : BookOrderRequest
 * author         : minsu11
 * date           : 2/15/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/15/24        minsu11       최초 생성
 */
@Getter
@NoArgsConstructor
@Setter
@ToString
public class BookOrderCreateRequest {

    @NotNull
    private List<BookInfoRequest> bookInfoList;

    private BookOrderInfoRequest orderInfo;
    @NotBlank
    @Size(max = 20)
    private String orderNumber;

    @PositiveOrZero
    private Integer pointCost;

    @PositiveOrZero
    private Integer couponCost;

    @Positive
    private Integer totalCost;


}

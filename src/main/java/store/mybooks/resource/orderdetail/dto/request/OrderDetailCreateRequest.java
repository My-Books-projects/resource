package store.mybooks.resource.orderdetail.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : store.mybooks.resource.order_detail.dto.request<br>
 * fileName       : OrderDetailCreateRequest<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
@Getter
@AllArgsConstructor
public class OrderDetailCreateRequest {
    @NotBlank
    private String orderNumber;
    @Positive
    private Long bookId;
    private Long couponId;
    private Integer wrapId;

    @PositiveOrZero
    @NotBlank
    private Integer bookCost;

    @Positive
    @NotBlank
    private Integer bookAmount;
}

package store.mybooks.resource.bookorder.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : store.mybooks.resource.bookorder.dto.request<br>
 * fileName       : BookOrderInfoRequest<br>
 * author         : minsu11<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/17/24        minsu11       최초 생성<br>
 */
@Getter
@NoArgsConstructor
@Setter
@ToString
public class BookOrderInfoRequest {

    @Positive
    private Integer deliveryId;

    @FutureOrPresent
    private LocalDate deliveryDate;


    @NotBlank
    @Size(min = 3, max = 20)
    private String recipientName;

    @NotBlank
    @Size(max = 100)
    private String recipientAddress;

    @NotBlank
    @Size(max = 13)
    private String recipientPhoneNumber;

    @NotBlank
    private String receiverMessage;

    private Integer usingPoint;
    @PositiveOrZero
    private Integer wrapCost;
    @PositiveOrZero
    private Integer couponApplicationAmount;
}

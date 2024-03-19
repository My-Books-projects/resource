package store.mybooks.resource.review.dto.reqeust;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : store.mybooks.resource.review.dto.reqeust<br>
 * fileName       : ReviewCreateRequest<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private Long orderDetailId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rate;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    private String content;

}

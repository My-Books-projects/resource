package store.mybooks.resource.review.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.mybooks.resource.image.dto.response.ImageResponse;

/**
 * packageName    : store.mybooks.resource.review.dto.response<br>
 * fileName       : ReviewGetResponse<br>
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
public class ReviewGetResponse {

    private Long bookId;

    private String bookName;

    private Long reviewId;

    private String userName;

    private Integer rate;

    private LocalDate date;

    private String title;

    private String content;

    private ImageResponse reviewImage;


}

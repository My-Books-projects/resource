package store.mybooks.resource.review.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.mybooks.resource.review.dto.response.ReviewDetailGetResponse;
import store.mybooks.resource.review.dto.response.ReviewGetResponse;

/**
 * packageName    : store.mybooks.resource.review.repository<br>
 * fileName       : ReviewRepositoryCustom<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */
@NoRepositoryBean
public interface ReviewRepositoryCustom {

    Page<ReviewGetResponse> getReviewByUserId(Long userId, Pageable pageable);

    Page<ReviewDetailGetResponse> getReviewByBookId(Long bookId, Pageable pageable);

    Optional<ReviewGetResponse> getReview(Long reviewId);

}

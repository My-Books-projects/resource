package store.mybooks.resource.review.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import store.mybooks.resource.review.dto.response.ReviewCreateResponse;
import store.mybooks.resource.review.dto.response.ReviewModifyResponse;
import store.mybooks.resource.review.entity.Review;

/**
 * packageName    : store.mybooks.resource.review.dto.mapper<br>
 * fileName       : ReviewMapper<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ReviewMapper {


    ReviewCreateResponse toReviewCreateResponse(Review review);

    ReviewModifyResponse toReviewModifyResponse(Review review);

}

package store.mybooks.resource.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.mybooks.resource.review.entity.Review;

/**
 * packageName    : store.mybooks.resource.review.repository <br/>
 * fileName       : ReviewRepository<br/>
 * author         : Fiat_lux <br/>
 * date           : 2/27/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2/27/24        Fiat_lux       최초 생성<br/>
 */
public interface ReviewRepository extends JpaRepository<Review, Long> ,ReviewRepositoryCustom{


    Boolean existsByOrderDetailId(Long orderDetailId);

}

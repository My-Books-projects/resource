package store.mybooks.resource.usercoupon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForMyPageQuerydsl;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForOrderQuerydsl;

/**
 * packageName    : store.mybooks.resource.user_coupon.repository
 * fileName       : UserCouponRepositoryCustom
 * author         : damho-lee
 * date           : 3/5/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/5/24          damho-lee          최초 생성
 */
@NoRepositoryBean
public interface UserCouponRepositoryCustom {
    /**
     * methodName : getUserCoupons <br>
     * author : damho-lee <br>
     * description : 회원 쿠폰 페이지에 보여줄 쿠폰 페이지 반환.<br>
     *
     * @param userId   Long
     * @param pageable Pageable
     * @return page
     */
    Page<UserCouponGetResponseForMyPageQuerydsl> getUserCoupons(Long userId, Pageable pageable);

    /**
     * methodName : getUsableUserCategoryCouponsByBookId <br>
     * author : damho-lee <br>
     * description : 사용 가능한 카테고리 쿠폰 리스트 반환.<br>
     *
     * @param userId Long
     * @param bookId Long
     * @return list
     */
    List<UserCouponGetResponseForOrderQuerydsl> getUsableUserCategoryCouponsByBookId(Long userId, Long bookId);

    /**
     * methodName : getUsableUserBookCouponsByBookId <br>
     * author : damho-lee <br>
     * description : 사용 가능한 도서 쿠폰 리스트 반환.<br>
     *
     * @param userId Long
     * @param bookId Long
     * @return list
     */
    List<UserCouponGetResponseForOrderQuerydsl> getUsableUserBookCouponsByBookId(Long userId, Long bookId);

    /**
     * methodName : getUsableTotalCoupons <br>
     * author : damho-lee <br>
     * description : 사용 가능한 전체 쿠폰 반환.<br>
     *
     * @param userId Long
     * @return list
     */
    List<UserCouponGetResponseForOrderQuerydsl> getUsableTotalCoupons(Long userId);

    /**
     * methodName : getUserCouponResponse <br>
     * author : minsu11 <br>
     * description : id로 쿠폰 조회.<br>
     *
     * @param userCouponId the user coupon id
     * @return the user coupon response
     */
    Optional<UserCouponGetResponseForOrderQuerydsl> getUserCouponResponse(Long userCouponId);
}

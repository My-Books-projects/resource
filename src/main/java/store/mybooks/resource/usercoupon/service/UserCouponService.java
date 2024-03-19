package store.mybooks.resource.usercoupon.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.coupon.entity.Coupon;
import store.mybooks.resource.coupon.exception.CouponNotExistsException;
import store.mybooks.resource.coupon.repository.CouponRepository;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.user.exception.UserNotExistException;
import store.mybooks.resource.user.repository.UserRepository;
import store.mybooks.resource.usercoupon.dto.request.UserCouponCreateRequest;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForMyPage;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForMyPageQuerydsl;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForOrder;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForOrderQuerydsl;
import store.mybooks.resource.usercoupon.entity.UserCoupon;
import store.mybooks.resource.usercoupon.exception.UserCouponNotExistsException;
import store.mybooks.resource.usercoupon.repository.UserCouponRepository;

/**
 * packageName    : store.mybooks.resource.user_coupon.service
 * fileName       : UserCouponService
 * author         : damho-lee
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24          damho-lee          최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    /**
     * methodName : getUserCoupons <br>
     * author : damho-lee <br>
     * description : 회원 쿠폰 페이지 요청. <br>
     *
     * @param userId   Long
     * @param pageable Pageable
     * @return page
     */
    @Transactional(readOnly = true)
    public Page<UserCouponGetResponseForMyPage> getUserCoupons(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotExistException(userId);
        }

        Page<UserCouponGetResponseForMyPageQuerydsl> userCouponGetResponsesPage =
                userCouponRepository.getUserCoupons(userId, pageable);
        List<UserCouponGetResponseForMyPage> userCouponGetResponseForMyPageList = new ArrayList<>();

        for (UserCouponGetResponseForMyPageQuerydsl userCouponGetResponseForMyPageQuerydsl
                : userCouponGetResponsesPage.getContent()) {
            userCouponGetResponseForMyPageList.add(makeCouponGetResponse(userCouponGetResponseForMyPageQuerydsl));
        }

        return new PageImpl<>(
                userCouponGetResponseForMyPageList,
                userCouponGetResponsesPage.getPageable(),
                userCouponGetResponsesPage.getTotalElements());
    }

    /**
     * methodName : getUsableUserCouponsByBookId <br>
     * author : damho-lee <br>
     * description : 사용가능한 회원 쿠폰 리스트 반환.<br>
     *
     * @param userId Long
     * @param bookId Long
     * @return list
     */
    @Transactional(readOnly = true)
    public List<UserCouponGetResponseForOrder> getUsableUserCouponsByBookId(Long userId, Long bookId) {
        List<UserCouponGetResponseForOrderQuerydsl> categoryCouponList =
                userCouponRepository.getUsableUserCategoryCouponsByBookId(userId, bookId);
        List<UserCouponGetResponseForOrderQuerydsl> bookCouponList =
                userCouponRepository.getUsableUserBookCouponsByBookId(userId, bookId);
        List<UserCouponGetResponseForOrder> userCouponGetResponseForOrderList = new ArrayList<>();

        for (UserCouponGetResponseForOrderQuerydsl userCouponGetResponseForOrderQuerydsl : bookCouponList) {
            userCouponGetResponseForOrderList.add(
                    makeCouponGetResponseForOrder(userCouponGetResponseForOrderQuerydsl));
        }
        for (UserCouponGetResponseForOrderQuerydsl userCouponGetResponseForOrderQuerydsl : categoryCouponList) {
            userCouponGetResponseForOrderList.add(
                    makeCouponGetResponseForOrder(userCouponGetResponseForOrderQuerydsl));
        }

        return userCouponGetResponseForOrderList;
    }

    /**
     * methodName : getUsableTotalCoupons <br>
     * author : damho-lee <br>
     * description : 사용 가능한 전체 쿠폰 리스트 반환.<br>
     *
     * @param userId Long
     * @return list
     */
    @Transactional(readOnly = true)
    public List<UserCouponGetResponseForOrder> getUsableTotalCoupons(Long userId) {
        List<UserCouponGetResponseForOrderQuerydsl> totalCouponList =
                userCouponRepository.getUsableTotalCoupons(userId);
        List<UserCouponGetResponseForOrder> userCouponGetResponseForOrderList = new ArrayList<>();

        for (UserCouponGetResponseForOrderQuerydsl userCouponGetResponseForOrderQuerydsl : totalCouponList) {
            userCouponGetResponseForOrderList.add(
                    makeCouponGetResponseForOrder(userCouponGetResponseForOrderQuerydsl));
        }

        return userCouponGetResponseForOrderList;
    }

    /**
     * methodName : getUserCoupon <br>
     * author : minsu11 <br>
     * description : id로 쿠폰 조회<br>
     *
     * @param userCouponId the coupon user id
     * @return the user coupon
     */
    @Transactional(readOnly = true)
    public UserCouponGetResponseForOrderQuerydsl getUserCoupon(Long userCouponId) {
        return userCouponRepository.getUserCouponResponse(userCouponId)
                .orElseThrow(() -> new UserCouponNotExistsException(userCouponId));
    }


    /**
     * methodName : createUserCoupon <br>
     * author : damho-lee <br>
     * description : 회원 쿠폰 생성.<br>
     *
     * @param request UserCouponCreateRequest
     */
    public void createUserCoupon(UserCouponCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotExistException(request.getUserId()));
        Coupon coupon = couponRepository.findById(request.getCouponId())
                .orElseThrow(() -> new CouponNotExistsException(request.getCouponId()));

        UserCoupon userCoupon = new UserCoupon(user, coupon);
        userCouponRepository.save(userCoupon);
    }


    /**
     * methodName : useUserCoupon <br>
     * author : damho-lee <br>
     * description : 회원 쿠폰 사용.<br>
     *
     * @param id Long
     */
    public void useUserCoupon(Long id) {
        UserCoupon userCoupon =
                userCouponRepository.findById(id).orElseThrow(() -> new UserCouponNotExistsException(id));
        userCoupon.use();
    }

    /**
     * methodName : giveBackUserCoupon <br>
     * author : damho-lee <br>
     * description : 쿠폰 돌려주는 메서드.<br>
     *
     * @param id Long
     */
    public void giveBackUserCoupon(Long id) {
        UserCoupon userCoupon =
                userCouponRepository.findById(id).orElseThrow(() -> new UserCouponNotExistsException(id));
        userCoupon.giveBack();
    }

    private UserCouponGetResponseForMyPage makeCouponGetResponse(UserCouponGetResponseForMyPageQuerydsl response) {
        Integer discountRateOrCost =
                response.isRate() ? response.getDiscountRate() : response.getDiscountCost();
        Integer maxDiscountCost =
                response.isRate() ? response.getMaxDiscountCost() : response.getDiscountCost();

        String range;
        String target;
        if (response.getCategoryName() == null && response.getBookName() == null) {
            range = "전체";
            target = "전체 도서";
        } else if (response.getBookName() != null) {
            range = "도서";
            target = response.getBookName();
        } else {
            range = "카테고리";
            target = response.getCategoryName();
        }

        return new UserCouponGetResponseForMyPage(
                response.getId(),
                response.getName(),
                range,
                target,
                response.getOrderMin(),
                discountRateOrCost,
                maxDiscountCost,
                response.isRate(),
                response.getStartDate(),
                response.getEndDate()
        );
    }

    private UserCouponGetResponseForOrder makeCouponGetResponseForOrder(
            UserCouponGetResponseForOrderQuerydsl response) {
        Integer discountRateOrCost =
                response.isRate() ? response.getDiscountRate() : response.getDiscountCost();
        Integer maxDiscountCost =
                response.isRate() ? response.getMaxDiscountCost() : response.getDiscountCost();

        return new UserCouponGetResponseForOrder(
                response.getUserCouponId(),
                response.getName(),
                response.getOrderMin(),
                discountRateOrCost,
                maxDiscountCost,
                response.isRate(),
                response.getStartDate(),
                response.getEndDate()
        );
    }
}

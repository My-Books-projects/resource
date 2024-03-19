package store.mybooks.resource.usercoupon.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import store.mybooks.resource.config.HeaderProperties;
import store.mybooks.resource.error.RequestValidationFailedException;
import store.mybooks.resource.usercoupon.dto.request.UserCouponCreateRequest;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForMyPage;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForOrder;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForOrderQuerydsl;
import store.mybooks.resource.usercoupon.service.UserCouponService;

/**
 * packageName    : store.mybooks.resource.user_coupon.controller
 * fileName       : UserCouponRestController
 * author         : damho-lee
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24          damho-lee          최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-coupon")
public class UserCouponRestController {
    private final UserCouponService userCouponService;

    /**
     * methodName : getUserCoupons <br>
     * author : damho-lee <br>
     * description : userId 로 회원 쿠폰 조회.<br>
     *
     * @param pageable Pageable
     * @param userId   Long
     * @return response entity
     */
    @GetMapping("/page")
    public ResponseEntity<Page<UserCouponGetResponseForMyPage>> getUserCoupons(
            @RequestHeader(name = HeaderProperties.USER_ID) Long userId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userCouponService.getUserCoupons(userId, pageable));
    }

    /**
     * methodName : getUsableUserCouponsByBookId <br>
     * author : damho-lee <br>
     * description : 사용 가능한 회원 쿠폰 조회. 각 도서에 적용할 수 있는 쿠폰.<br>
     *
     * @param userId Long
     * @param bookId Long
     * @return response entity
     */
    @GetMapping("/usable-coupon/{bookId}")
    public ResponseEntity<List<UserCouponGetResponseForOrder>> getUsableUserCouponsByBookId(
            @RequestHeader(name = HeaderProperties.USER_ID) Long userId, @PathVariable("bookId") Long bookId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userCouponService.getUsableUserCouponsByBookId(userId, bookId));
    }

    /**
     * methodName : getUsableTotalCoupons <br>
     * author : damho-lee <br>
     * description : 사용 가능한 회원 쿠폰 조회. 전체 적용 쿠폰.<br>
     *
     * @param userId Long
     * @return response entity
     */
    @GetMapping("/usable-coupon")
    public ResponseEntity<List<UserCouponGetResponseForOrder>> getUsableTotalCoupons(
            @RequestHeader(name = HeaderProperties.USER_ID) Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userCouponService.getUsableTotalCoupons(userId));
    }

    /**
     * methodName : createUserCoupon <br>
     * author : damho-lee <br>
     * description : 회원 쿠폰 생성.<br>
     *
     * @param request       UserCouponCreateRequest
     * @param bindingResult BindingResult
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<Void> createUserCoupon(@Valid @RequestBody UserCouponCreateRequest request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationFailedException(bindingResult);
        }

        userCouponService.createUserCoupon(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * methodName : useUserCoupon <br>
     * author : damho-lee <br>
     * description : 쿠폰 사용.<br>
     *
     * @param id Long
     * @return response entity
     */
    @PutMapping("/use/{userCouponId}")
    public ResponseEntity<Void> useUserCoupon(@PathVariable("userCouponId") Long id) {
        userCouponService.useUserCoupon(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * methodName : returnUserCoupon <br>
     * author : damho-lee <br>
     * description : 쿠폰 돌려주는 메서드.<br>
     *
     * @param id Long
     * @return response entity
     */
    @PutMapping("/return/{userCouponId}")
    public ResponseEntity<Void> giveBackUserCoupon(@PathVariable("userCouponId") Long id) {
        userCouponService.giveBackUserCoupon(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * methodName : getUserCoupon <br>
     * author : minsu11 <br>
     * description : id로 쿠폰 조회.<br>
     *
     * @param couponUserId the coupon user id
     * @return the user coupon
     */
    @GetMapping("/{couponUserId}")
    public ResponseEntity<UserCouponGetResponseForOrderQuerydsl> getUserCoupon(@PathVariable Long couponUserId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userCouponService.getUserCoupon(couponUserId));

    }
}

package store.mybooks.resource.usercoupon.repository;

import com.querydsl.core.types.Projections;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.book.entity.QBook;
import store.mybooks.resource.bookcategory.entity.QBookCategory;
import store.mybooks.resource.category.entity.QCategory;
import store.mybooks.resource.coupon.entity.QCoupon;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForMyPageQuerydsl;
import store.mybooks.resource.usercoupon.dto.response.UserCouponGetResponseForOrderQuerydsl;
import store.mybooks.resource.usercoupon.entity.QUserCoupon;
import store.mybooks.resource.usercoupon.entity.UserCoupon;

/**
 * packageName    : store.mybooks.resource.user_coupon.repository
 * fileName       : UserCouponRepositoryImpl
 * author         : damho-lee
 * date           : 3/5/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/5/24          damho-lee          최초 생성
 */
public class UserCouponRepositoryImpl extends QuerydslRepositorySupport implements UserCouponRepositoryCustom {
    public UserCouponRepositoryImpl() {
        super(UserCoupon.class);
    }


    @Override
    public Page<UserCouponGetResponseForMyPageQuerydsl> getUserCoupons(Long userId, Pageable pageable) {
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;
        QBook book = QBook.book;
        QCategory category = QCategory.category;

        List<UserCouponGetResponseForMyPageQuerydsl> userCouponGetResponseList =
                from(userCoupon)
                        .leftJoin(coupon)
                        .on(userCoupon.coupon.id.eq(coupon.id))
                        .leftJoin(book)
                        .on(coupon.book.id.eq(book.id))
                        .leftJoin(category)
                        .on(coupon.category.id.eq(category.id))
                        .where(userCoupon.user.id.eq(userId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .select(Projections.constructor(UserCouponGetResponseForMyPageQuerydsl.class,
                                userCoupon.id,
                                coupon.name,
                                coupon.orderMin,
                                coupon.discountCost,
                                coupon.maxDiscountCost,
                                coupon.discountRate,
                                coupon.isRate,
                                coupon.startDate,
                                coupon.endDate,
                                book.name,
                                category.name))
                        .fetch();

        long total = from(userCoupon)
                .where(userCoupon.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(userCouponGetResponseList, pageable, total);
    }

    @Override
    public List<UserCouponGetResponseForOrderQuerydsl> getUsableUserCategoryCouponsByBookId(Long userId, Long bookId) {
        QCategory category1 = new QCategory("category1");
        QCategory category2 = new QCategory("category2");
        QCategory category3 = new QCategory("category3");
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;

        Set<Integer> categoryIdSet = from(category1)
                .leftJoin(category2).on(category1.parentCategory.id.eq(category2.id))
                .leftJoin(category3).on(category2.parentCategory.id.eq(category3.id))
                .leftJoin(bookCategory).on(category1.id.eq(bookCategory.category.id))
                .where(bookCategory.book.id.eq(bookId))
                .select(category1.id, category2.id, category3.id)
                .fetch()
                .stream()
                .flatMap(tuple -> Stream.of(tuple.get(category1.id), tuple.get(category2.id),
                        tuple.get(category3.id))).filter(Objects::nonNull)
                .map(Number::intValue).collect(Collectors.toSet());

        return from(userCoupon)
                .leftJoin(coupon)
                .on(userCoupon.coupon.id.eq(coupon.id))
                .where(userCoupon.user.id.eq(userId))
                .where(userCoupon.isUsed.isFalse())
                .where(coupon.category.id.in(categoryIdSet))
                .where(coupon.startDate.loe(LocalDate.now()))
                .where(coupon.endDate.goe(LocalDate.now()))
                .select(Projections.constructor(UserCouponGetResponseForOrderQuerydsl.class,
                        userCoupon.id,
                        coupon.name,
                        coupon.orderMin,
                        coupon.discountCost,
                        coupon.maxDiscountCost,
                        coupon.discountRate,
                        coupon.isRate,
                        coupon.startDate,
                        coupon.endDate))
                .fetch();
    }

    @Override
    public List<UserCouponGetResponseForOrderQuerydsl> getUsableUserBookCouponsByBookId(Long userId, Long bookId) {
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;

        return from(userCoupon)
                .leftJoin(coupon)
                .on(userCoupon.coupon.id.eq(coupon.id))
                .where(userCoupon.user.id.eq(userId))
                .where(coupon.book.id.eq(bookId))
                .where(userCoupon.isUsed.isFalse())
                .where(coupon.startDate.loe(LocalDate.now()))
                .where(coupon.endDate.goe(LocalDate.now()))
                .select(Projections.constructor(UserCouponGetResponseForOrderQuerydsl.class,
                        userCoupon.id,
                        coupon.name,
                        coupon.orderMin,
                        coupon.discountCost,
                        coupon.maxDiscountCost,
                        coupon.discountRate,
                        coupon.isRate,
                        coupon.startDate,
                        coupon.endDate))
                .fetch();
    }

    @Override
    public List<UserCouponGetResponseForOrderQuerydsl> getUsableTotalCoupons(Long userId) {
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        QCoupon coupon = QCoupon.coupon;

        return from(userCoupon)
                .leftJoin(coupon)
                .on(userCoupon.coupon.id.eq(coupon.id))
                .where(userCoupon.user.id.eq(userId))
                .where(userCoupon.isUsed.isFalse())
                .where(coupon.book.isNull())
                .where(coupon.category.isNull())
                .where(coupon.startDate.loe(LocalDate.now()))
                .where(coupon.endDate.goe(LocalDate.now()))
                .select(Projections.constructor(UserCouponGetResponseForOrderQuerydsl.class,
                        userCoupon.id,
                        coupon.name,
                        coupon.orderMin,
                        coupon.discountCost,
                        coupon.maxDiscountCost,
                        coupon.discountRate,
                        coupon.isRate,
                        coupon.startDate,
                        coupon.endDate))
                .fetch();
    }

    @Override
    public Optional<UserCouponGetResponseForOrderQuerydsl> getUserCouponResponse(Long userCouponId) {
        QCoupon coupon = QCoupon.coupon;
        QUserCoupon userCoupon = QUserCoupon.userCoupon;
        return Optional.ofNullable(
                from(userCoupon)
                        .select(Projections.constructor(UserCouponGetResponseForOrderQuerydsl.class,
                                userCoupon.id,
                                coupon.name,
                                coupon.orderMin,
                                coupon.discountCost,
                                coupon.maxDiscountCost,
                                coupon.discountRate,
                                coupon.isRate,
                                coupon.startDate,
                                coupon.endDate)
                        )
                        .where(userCoupon.id.eq(userCouponId))
                        .fetchOne()
        );
    }
}

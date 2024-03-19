package store.mybooks.resource.review.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.book.entity.QBook;
import store.mybooks.resource.image.dto.response.ImageResponse;
import store.mybooks.resource.image.entity.Image;
import store.mybooks.resource.image.entity.QImage;
import store.mybooks.resource.image_status.entity.QImageStatus;
import store.mybooks.resource.orderdetail.entity.QOrderDetail;
import store.mybooks.resource.review.dto.response.ReviewDetailGetResponse;
import store.mybooks.resource.review.dto.response.ReviewGetResponse;
import store.mybooks.resource.review.entity.QReview;
import store.mybooks.resource.review.entity.Review;
import store.mybooks.resource.user.entity.QUser;

/**
 * packageName    : store.mybooks.resource.review.repository<br>
 * fileName       : ReviewRepositoryImpl<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */
public class ReviewRepositoryImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

    QReview review = QReview.review;

    QImage image = QImage.image;

    QImageStatus imageStatus = QImageStatus.imageStatus;

    QUser user = QUser.user;

    QBook book = QBook.book;

    QOrderDetail orderDetail = QOrderDetail.orderDetail;


    @Override
    public Page<ReviewGetResponse> getReviewByUserId(Long userId, Pageable pageable) {


        List<Review> reviews = from(review)
                .where(review.user.id.eq(userId))
                .select(review)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Image> images = from(image)
                .join(review)
                .on(review.id.eq(image.review.id))
                .fetch();

        List<ReviewGetResponse> responseList = new ArrayList<>();
        for (Review review : reviews) {

            Optional<ImageResponse> imageResponseOptional = images.stream()
                    .filter(image -> image.getReview().getId().equals(review.getId()))
                    .map(image -> new ImageResponse(image.getPath(), image.getFileName(), image.getExtension()))
                    .findFirst();

            ImageResponse imageResponse = null;

            if (imageResponseOptional.isPresent()) {
                imageResponse = imageResponseOptional.get();
            }

            ReviewGetResponse response = new ReviewGetResponse(
                    review.getOrderDetail().getBook().getId(),
                    review.getOrderDetail().getBook().getName(),
                    review.getId(),
                    review.getUser().getName(),
                    review.getRate(),
                    review.getDate(),
                    review.getTitle(),
                    review.getContent(),
                    imageResponse
            );
            responseList.add(response);
        }

        // 전체 리뷰 개수 조회
        long total = from(review)
                .where(review.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(responseList, pageable, total);
    }

    @Override
    public Page<ReviewDetailGetResponse> getReviewByBookId(Long bookId, Pageable pageable) {

        List<Review> reviews = from(review)
                .where(review.orderDetail.book.id.eq(bookId))
                .select(review)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Image> images = from(image)
                .join(review)
                .on(review.id.eq(image.review.id))
                .fetch();

        List<ReviewDetailGetResponse> responseList = new ArrayList<>();
        for (Review review : reviews) {

            Optional<ImageResponse> imageResponseOptional = images.stream()
                    .filter(image -> image.getReview().getId().equals(review.getId()))
                    .map(image -> new ImageResponse(image.getPath(), image.getFileName(), image.getExtension()))
                    .findFirst();

            ImageResponse imageResponse = null;

            if (imageResponseOptional.isPresent()) {
                imageResponse = imageResponseOptional.get();
            }

            ReviewDetailGetResponse response = new ReviewDetailGetResponse(
                    review.getId(),
                    review.getUser().getName(),
                    review.getRate(),
                    review.getDate(),
                    review.getTitle(),
                    review.getContent(),
                    imageResponse
            );
            responseList.add(response);
        }

        long total = from(review)
                .where(review.orderDetail.book.id.eq(bookId))
                .fetchCount();

        return new PageImpl<>(responseList, pageable, total);
    }

    @Override
    public Optional<ReviewGetResponse> getReview(Long reviewId) {

        List<Review> reviews = from(review)
                .where(review.id.eq(reviewId))
                .select(review)
                .fetch();

        List<Image> images = from(image)
                .join(review)
                .on(review.id.eq(image.review.id))
                .fetch();


        ReviewGetResponse response = null;
        for (Review review : reviews) {

            Optional<ImageResponse> imageResponseOptional = images.stream()
                    .filter(image -> image.getReview().getId().equals(review.getId()))
                    .map(image -> new ImageResponse(image.getPath(), image.getFileName(), image.getExtension()))
                    .findFirst();

            ImageResponse imageResponse = null;

            if (imageResponseOptional.isPresent()) {
                imageResponse = imageResponseOptional.get();
            }

            response = new ReviewGetResponse(
                    review.getOrderDetail().getBook().getId(),
                    review.getOrderDetail().getBook().getName(),
                    review.getId(),
                    review.getUser().getName(),
                    review.getRate(),
                    review.getDate(),
                    review.getTitle(),
                    review.getContent(),
                    imageResponse
            );
        }
        return Optional.of(response);
    }
}

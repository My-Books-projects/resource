package store.mybooks.resource.review.exception;

/**
 * packageName    : store.mybooks.resource.review.exception<br>
 * fileName       : ReviewNotExistException<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */
public class ReviewNotExistException extends RuntimeException {
    public ReviewNotExistException(Long reviewId) {
        super(String.format("[%d]는 존재하지 않는 리뷰입니다", reviewId));
    }

}

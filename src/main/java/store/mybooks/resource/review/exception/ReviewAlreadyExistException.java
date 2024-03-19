package store.mybooks.resource.review.exception;

/**
 * packageName    : store.mybooks.resource.review.exception<br>
 * fileName       : ReviewAlreadyExistException<br>
 * author         : masiljangajji<br>
 * date           : 3/17/24<br>
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        masiljangajji       최초 생성
 */
public class ReviewAlreadyExistException extends RuntimeException{
    public ReviewAlreadyExistException(Long orderDetailId) {
        super(String.format("[%d]에대해 이미 리뷰가 존재합니다.",orderDetailId));
    }
}

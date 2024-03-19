package store.mybooks.resource.bookorder.exception;

/**
 * packageName    : store.mybooks.resource.bookorder.exception<br>
 * fileName       : BookOrderInfoNotMatchException<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
public class BookOrderInfoNotMatchException extends RuntimeException {
    public BookOrderInfoNotMatchException() {
        super("주문 정보가 일치하지 않음");
    }
}

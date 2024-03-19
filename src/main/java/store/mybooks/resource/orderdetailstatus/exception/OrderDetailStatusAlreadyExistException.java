package store.mybooks.resource.orderdetailstatus.exception;

/**
 * packageName    : store.mybooks.resource.order_detail_status.exception
 * fileName       : OrderDetailStatusAlreadyExistException
 * author         : minsu11
 * date           : 2/16/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/16/24        minsu11       최초 생성
 */
public class OrderDetailStatusAlreadyExistException extends RuntimeException {
    public OrderDetailStatusAlreadyExistException() {
    }

    public OrderDetailStatusAlreadyExistException(String message) {
        super(message);
    }
}

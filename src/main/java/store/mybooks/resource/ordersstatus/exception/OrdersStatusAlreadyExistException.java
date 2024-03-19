package store.mybooks.resource.ordersstatus.exception;

/**
 * packageName    : store.mybooks.resource.orders_status.exception
 * fileName       : OrdersStatusAlreadyExistException
 * author         : minsu11
 * date           : 2/15/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/15/24        minsu11       최초 생성
 */
public class OrdersStatusAlreadyExistException extends RuntimeException {
    public OrdersStatusAlreadyExistException() {
    }

    public OrdersStatusAlreadyExistException(String message) {
        super(message);
    }
}

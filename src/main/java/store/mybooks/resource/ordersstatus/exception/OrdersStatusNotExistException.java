package store.mybooks.resource.ordersstatus.exception;

/**
 * packageName    : store.mybooks.resource.orders_status.exception
 * fileName       : OrdersStatusNotFoundException
 * author         : minsu11
 * date           : 2/15/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/15/24        minsu11       최초 생성
 */
public class OrdersStatusNotExistException extends RuntimeException {
    public OrdersStatusNotExistException() {
        super("주문 상태가 없음");
    }

}

package store.mybooks.resource.bookorder.eumulation;

/**
 * packageName    : store.mybooks.resource.bookorder.eumulation<br>
 * fileName       : BookOrderStatusName<br>
 * author         : minsu11<br>
 * date           : 3/19/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/19/24        minsu11       최초 생성<br>
 */
public enum BookOrderStatusName {
    ORDER_WAIT("주문 대기"),
    PAY_WAIT("결제 대기"),
    ORDER_COMPLETED("주문 완료"),
    ORDER_CANCEL("주문 취소"),
    DELIVERING("배송 중"),
    DELIVERY_COMPLETED("배송 완료"),
    PAY_COMPLETED("구매 완료");

    private final String value;

    BookOrderStatusName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

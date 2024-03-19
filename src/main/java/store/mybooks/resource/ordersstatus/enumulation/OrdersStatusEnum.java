package store.mybooks.resource.ordersstatus.enumulation;

/**
 * packageName    : store.mybooks.resource.orders_status.enumulation<br>
 * fileName       : OrdersStatusEnum<br>
 * author         : minsu11<br>
 * date           : 3/3/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/3/24        minsu11       최초 생성<br>
 */
public enum OrdersStatusEnum {

    WAIT("주문 대기"),

    DELIVERY("배송 중"),
    DELIVERY_COMPLETE("배송 완료"),

    PAY_COMPLETE("구매 완료");


    private final String stauts;

    OrdersStatusEnum(String name) {
        this.stauts = name;
    }

    @Override
    public String toString() {
        return stauts;
    }
}

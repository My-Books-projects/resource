package store.mybooks.resource.ordersstatus.repository;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import store.mybooks.resource.ordersstatus.dto.response.OrdersStatusResponse;
import store.mybooks.resource.ordersstatus.entity.OrdersStatus;

/**
 * packageName    : store.mybooks.resource.orders_status.repository<br>
 * fileName       : OrdersStatusRepositoryTest<br>
 * author         : minsu11<br>
 * date           : 2/20/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/20/24        minsu11       최초 생성<br>
 */
@DataJpaTest
class OrdersStatusRepositoryTest {

    @Autowired
    OrdersStatusRepository ordersStatusRepository;

    @BeforeEach
    void setUp() {
        OrdersStatus ordersStatus1 = new OrdersStatus("test1");
        OrdersStatus ordersStatus2 = new OrdersStatus("test2");
        ordersStatusRepository.save(ordersStatus1);
        ordersStatusRepository.save(ordersStatus2);
    }

    @Test
    @Order(1)
    @DisplayName("id 값을 parameter로 받을 때 조회가 성공적으로 되는 테스트")
    void givenOrdersStatusId_whenFindById_thenReturnOrdersStatus() {
        Optional<OrdersStatus> ordersStatus = ordersStatusRepository.findById("test1");
        Assertions.assertEquals("test1", ordersStatus.get().getId());
    }

    @Test
    @Order(2)
    @DisplayName("DB에 없는 id 값으로 조회하는 테스트")
    void givenNotMatchOrdersStatusId_whenFindById_thenReturnNoSuchElementException() {
        Assertions.assertEquals(Optional.empty(), ordersStatusRepository.findById("test3"));
    }

    @Test
    @Order(3)
    void given_whenGetOrdersStatusList_thenReturnOrderStatusResponseList() {
        List<OrdersStatusResponse> actual = ordersStatusRepository.getOrdersStatusList();
        Assertions.assertEquals("test1", actual.get(0).getId());
        Assertions.assertEquals(2, actual.size());
    }
}
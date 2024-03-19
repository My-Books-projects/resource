package store.mybooks.resource.orderdetailstatus.repository;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import store.mybooks.resource.orderdetailstatus.dto.response.OrderDetailStatusResponse;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;
import store.mybooks.resource.ordersstatus.exception.OrdersStatusNotExistException;

/**
 * packageName    : store.mybooks.resource.order_detail_status.repository<br>
 * fileName       : OrderDetailStatusRepositoryTest<br>
 * author         : minsu11<br>
 * date           : 2/21/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/21/24        minsu11       최초 생성<br>
 */
@DataJpaTest
class OrderDetailStatusRepositoryTest {
    @Autowired
    OrderDetailStatusRepository orderDetailStatusRepository;

    @BeforeEach
    void setUp() {
        OrderDetailStatus orderDetailStatusValue1 = new OrderDetailStatus("test1");
        OrderDetailStatus orderDetailStatusValue2 = new OrderDetailStatus("test2");
        orderDetailStatusRepository.save(orderDetailStatusValue1);
        orderDetailStatusRepository.save(orderDetailStatusValue2);
    }

    @Test
    @Order(1)
    @DisplayName("id 값으로 상세 주문 상태 조회")
    void givenStringId_whenFindById_thenReturnOrderDetailStatus() {
        String testData = "test1";
        OrderDetailStatus expected = new OrderDetailStatus(testData);
        OrderDetailStatus actual = orderDetailStatusRepository.findById(testData).orElseThrow(OrdersStatusNotExistException::new);

        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    @Order(2)
    @DisplayName("id 값으로 상세 주문 상태 조회 시 없는 경우")
    void givenStringId_whenFindById_thenReturnOptionalEmpty() {
        String testDataId = "test3";
        Assertions.assertEquals(Optional.empty(), orderDetailStatusRepository.findById(testDataId));
    }

    @Test
    @Order(3)
    @DisplayName("상세 주문 상태의 모든 데이터 조회")
    void given_whenGetOrderDetailStatusResponseList_thenReturnOrderDetailStatusResponseList() {
        List<OrderDetailStatusResponse> expected =
                List.of(new OrderDetailStatusResponse("test1"), new OrderDetailStatusResponse("test2"));
        List<OrderDetailStatusResponse> actual =
                orderDetailStatusRepository.getOrderDetailStatusResponseList();

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.get(1).getId(), actual.get(1).getId());

    }

    @Test
    @Order(4)
    @DisplayName("DB에 상세 주문 상태 정보가 없는 경우")
    void given_whenGetOrderDetailStatus_thenReturnEmptyList() {
        orderDetailStatusRepository.deleteAll();

        Assertions.assertEquals(0, orderDetailStatusRepository.getOrderDetailStatusResponseList().size());
    }
}
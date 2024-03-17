package store.mybooks.resource.bookorder.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.bookorder.dto.mapper.BookOrderMapper;
import store.mybooks.resource.bookorder.dto.request.BookOrderAdminModifyRequest;
import store.mybooks.resource.bookorder.dto.request.BookOrderCreateRequest;
import store.mybooks.resource.bookorder.dto.request.BookOrderInfoRequest;
import store.mybooks.resource.bookorder.dto.request.BookOrderRegisterInvoiceRequest;
import store.mybooks.resource.bookorder.dto.response.BookOrderCreateResponse;
import store.mybooks.resource.bookorder.dto.response.BookOrderRegisterInvoiceResponse;
import store.mybooks.resource.bookorder.dto.response.BookOrderUserResponse;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminModifyResponse;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminResponse;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.bookorder.exception.BookOrderInfoNotMatchException;
import store.mybooks.resource.bookorder.exception.BookOrderNotExistException;
import store.mybooks.resource.bookorder.repository.BookOrderRepository;
import store.mybooks.resource.delivery_rule.entity.DeliveryRule;
import store.mybooks.resource.delivery_rule.exception.DeliveryRuleNotExistsException;
import store.mybooks.resource.delivery_rule.repository.DeliveryRuleRepository;
import store.mybooks.resource.order_detail.dto.response.OrderDetailCreateResponse;
import store.mybooks.resource.orders_status.entity.OrdersStatus;
import store.mybooks.resource.orders_status.enumulation.OrdersStatusEnum;
import store.mybooks.resource.orders_status.exception.OrdersStatusNotExistException;
import store.mybooks.resource.orders_status.repository.OrdersStatusRepository;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.user.exception.UserNotExistException;
import store.mybooks.resource.user.repository.UserRepository;
import store.mybooks.resource.user_address.repository.UserAddressRepository;

/**
 * packageName    : store.mybooks.resource.book_order.service<br>
 * fileName       : BookOrderService<br>
 * author         : minsu11<br>
 * date           : 3/2/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/2/24        minsu11       최초 생성<br>
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookOrderService {
    private final BookOrderRepository bookOrderRepository;
    private final OrdersStatusRepository ordersStatusRepository;
    private final BookOrderMapper bookOrderMapper;
    private final UserAddressRepository userAddressRepository;
    private final DeliveryRuleRepository deliveryRuleRepository;
    private final UserRepository userRepository;

    /**
     * methodName : getBookOrderResponseList<br>
     * author : minsu11<br>
     * description : 회원아디로 조회한 후 회원의 주문 내역 목록 페이징
     * <br> *
     *
     * @param userId   조회할 회원 아이디
     * @param pageable 페이징 관련 정보
     * @return page
     */
    @Transactional(readOnly = true)
    public Page<BookOrderUserResponse> getBookOrderResponseList(Long userId, Pageable pageable) {

        return bookOrderRepository.getBookOrderPageByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookOrderAdminResponse> getBookOrderAdminResponseList(Pageable pageable) {
        return bookOrderRepository.getBookOrderPageByOrderStatusId(pageable);
    }

    /**
     * methodName : modifyBookOrderStatus<br>
     * author : minsu11<br>
     * description : 관리자가 주문 상태를 변경.
     * <br> *
     *
     * @param request 변경할 주문 상태가 들어있느 DTO
     * @return book order modify order status request
     */
    public BookOrderAdminModifyResponse modifyBookOrderAdminStatus(BookOrderAdminModifyRequest request) {
        OrdersStatus ordersStatus = ordersStatusRepository.findById(OrdersStatusEnum.DELIVERY.toString())
                .orElseThrow(OrdersStatusNotExistException::new);

        BookOrder bookOrder = bookOrderRepository.findById(request.getId()).orElseThrow(BookOrderNotExistException::new);
        bookOrder.modifyBookOrderAdmin(ordersStatus);
        return bookOrderMapper.mapToBookOrderModifyOrderStatusResponse(bookOrder);
    }

    /**
     * methodName : registerBookOrderInvoiceNumber<br>
     * author : minsu11<br>
     * description : 주문의 송장 번호 등록. 주문이 없을 시 {@code BookorderNotExistException}을 던짐.
     * <br> *
     *
     * @param request 등록할 송장 번호.
     * @return book order register invoice response
     */
    public BookOrderRegisterInvoiceResponse registerBookOrderInvoiceNumber(BookOrderRegisterInvoiceRequest request) {
        BookOrder bookOrder = bookOrderRepository.findById(request.getId()).orElseThrow(BookOrderNotExistException::new);

        bookOrder.registerBookOrderInvoiceNumber(request.getInvoiceNumber());
        return bookOrderMapper.mapToBookOrderRegisterInvoiceResponse(bookOrder);
    }

    /**
     * methodName : checkUserOrderAddress<br>
     * author : minsu11<br>
     * description : 주문에서 입력 들어온 회원의 주소가 맞는지 판별.
     * <br> *
     *
     * @param addressId 회원의 주소 아이디
     * @return book order register invoice response
     */
    public void checkUserOrderAddress(Long addressId) {
        if (!userAddressRepository.existsById(addressId)) {
            throw new BookOrderInfoNotMatchException();
        }
    }

    /**
     * methodName : createBookOrder<br>
     * author : minsu11<br>
     * description : 주문서 생성.
     * <br> *
     *
     * @param request 등록할 송장 번호.
     * @return book order register invoice response
     */
    public BookOrderCreateResponse createBookOrder(BookOrderCreateRequest request, Long userId) {
        BookOrderInfoRequest orderInfo = request.getOrderInfo();
        DeliveryRule deliveryRule = deliveryRuleRepository.findById(orderInfo.getDeliveryId())
                .orElseThrow(() -> new DeliveryRuleNotExistsException("배송 규정 없음"));
        OrdersStatus ordersStatus = ordersStatusRepository.findById("주문 대기").orElseThrow(OrdersStatusNotExistException::new);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId));
        BookOrder bookOrder = BookOrder.builder()
                .user(user)
                .deliveryRule(deliveryRule)
                .receiverName(orderInfo.getRecipientName())
                .receiverAddress(orderInfo.getRecipientAddress())
                .receiverMessage(orderInfo.getReceiverMessage())
                .receiverPhoneNumber(orderInfo.getRecipientPhoneNumber())
                .orderStatus(ordersStatus)
                .deliveryDate(orderInfo.getDeliveryDate())
                .date(LocalDate.now())
                .number(request.getOrderNumber())
                .totalCost(request.getTotalCost())
                .couponCost(request.getCouponCost())
                .userCoupon(null)
                .isCouponUsed(false)
                .pointCost(request.getPointCost())
                .build();

        bookOrderRepository.save(bookOrder);
        return bookOrderMapper.mapToBookOrderCreateResponse(bookOrder);
    }

    /**
     * {@code orderNubmer}로 된 주문이 있는지 확인하는 메서드.
     *
     * @param orderNumber the order number
     * @return the boolean
     */
    public Boolean checkBookOrderNumberExists(String orderNumber) {
        return bookOrderRepository.existBookOrderByOrderNumber(orderNumber);
    }

    /**
     * 개별 쿠폰이 적용이 되었는지 확인.
     *
     * @param orderDetailList the order detail list
     * @return the boolean
     */
    public Boolean checkCouponUsed(List<OrderDetailCreateResponse> orderDetailList) {
        for (OrderDetailCreateResponse orderDetail : orderDetailList) {
            System.out.println("확인 ");
            System.out.println(orderDetail.getIsCouponUsed());
            boolean check = orderDetail.getIsCouponUsed();
            System.out.println("쿠폰 사용 유무 : " + check);
            if (check) {
                return true;
            }
        }
        return false;
    }

}

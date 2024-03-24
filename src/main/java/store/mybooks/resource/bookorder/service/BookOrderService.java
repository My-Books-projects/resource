package store.mybooks.resource.bookorder.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.bookorder.dto.mapper.BookOrderMapper;
import store.mybooks.resource.bookorder.dto.request.BookOrderAdminModifyRequest;
import store.mybooks.resource.bookorder.dto.request.BookOrderCreateRequest;
import store.mybooks.resource.bookorder.dto.request.BookOrderInfoRequest;
import store.mybooks.resource.bookorder.dto.request.BookOrderRegisterInvoiceRequest;
import store.mybooks.resource.bookorder.dto.response.*;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminModifyResponse;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminResponse;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.bookorder.eumulation.BookOrderStatusName;
import store.mybooks.resource.bookorder.exception.BookOrderInfoNotMatchException;
import store.mybooks.resource.bookorder.exception.BookOrderNotExistException;
import store.mybooks.resource.bookorder.repository.BookOrderRepository;
import store.mybooks.resource.bookstatus.respository.BookStatusRepository;
import store.mybooks.resource.delivery_rule.entity.DeliveryRule;
import store.mybooks.resource.delivery_rule.exception.DeliveryRuleNotExistsException;
import store.mybooks.resource.delivery_rule.repository.DeliveryRuleRepository;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailCreateResponse;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailInfoResponse;
import store.mybooks.resource.orderdetail.repository.OrderDetailRepository;
import store.mybooks.resource.ordersstatus.entity.OrdersStatus;
import store.mybooks.resource.ordersstatus.enumulation.OrdersStatusEnum;
import store.mybooks.resource.ordersstatus.exception.OrdersStatusNotExistException;
import store.mybooks.resource.ordersstatus.repository.OrdersStatusRepository;
import store.mybooks.resource.user.entity.User;
import store.mybooks.resource.user.exception.UserNotExistException;
import store.mybooks.resource.user.repository.UserRepository;
import store.mybooks.resource.user_address.repository.UserAddressRepository;
import store.mybooks.resource.utils.TimeUtils;

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
    private final BookStatusRepository bookStatusRepository;
    private final OrdersStatusRepository ordersStatusRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final BookOrderMapper bookOrderMapper;
    private final UserAddressRepository userAddressRepository;
    private final DeliveryRuleRepository deliveryRuleRepository;
    private final UserRepository userRepository;

    /**
     * methodName : getBookOrderResponseList<br>
     * author : minsu11<br>
     * description : 회원아디로 조회한 후 회원의 주문 내역 목록 페이징.
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

        BookOrder bookOrder = bookOrderRepository.findById(request.getId())
                .orElseThrow(BookOrderNotExistException::new);
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
        BookOrder bookOrder = bookOrderRepository.findById(request.getId())
                .orElseThrow(BookOrderNotExistException::new);

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
    public boolean checkUserOrderAddress(Long addressId) {
        if (!userAddressRepository.existsById(addressId)) {
            throw new BookOrderInfoNotMatchException();
        }
        return true;
    }

    /**
     * methodName : createBookOrder<br>
     * author : minsu11<br>
     * description : 주문서 생성.
     * <br> *
     *
     * @param request 주문서 생성할 dto.
     * @return book order register invoice response
     */
    public BookOrderCreateResponse createBookOrder(BookOrderCreateRequest request, Long userId) {
        BookOrderInfoRequest orderInfo = request.getOrderInfo();
        DeliveryRule deliveryRule = deliveryRuleRepository.findById(orderInfo.getDeliveryId())
                .orElseThrow(() -> new DeliveryRuleNotExistsException("배송 규정 없음"));
        OrdersStatus ordersStatus = ordersStatusRepository.findById(BookOrderStatusName.ORDER_WAIT.toString())
                .orElseThrow(OrdersStatusNotExistException::new);
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
                .date(TimeUtils.nowDate())
                .number(request.getOrderNumber())
                .totalCost(request.getTotalCost())
                .couponCost(request.getCouponCost())
                .userCoupon(null)
                .isCouponUsed(false)
                .pointCost(request.getPointCost())
                .findPassword(request.getOrderCode())
                .build();

        bookOrderRepository.save(bookOrder);
        return bookOrderMapper.mapToBookOrderCreateResponse(bookOrder);
    }

    public BookOrderCreateResponse createBookOrderNonUser(BookOrderCreateRequest request) {
        BookOrderInfoRequest orderInfo = request.getOrderInfo();
        DeliveryRule deliveryRule = deliveryRuleRepository.findById(orderInfo.getDeliveryId())
                .orElseThrow(() -> new DeliveryRuleNotExistsException("배송 규정 없음"));
        OrdersStatus ordersStatus = ordersStatusRepository.findById(BookOrderStatusName.ORDER_WAIT.toString())
                .orElseThrow(OrdersStatusNotExistException::new);
        User user = userRepository.findById(0L).orElseThrow(() -> new UserNotExistException(0L));
        BookOrder bookOrder = BookOrder.builder()
                .user(user)
                .deliveryRule(deliveryRule)
                .receiverName(orderInfo.getRecipientName())
                .receiverAddress(orderInfo.getRecipientAddress())
                .receiverMessage(orderInfo.getReceiverMessage())
                .receiverPhoneNumber(orderInfo.getRecipientPhoneNumber())
                .orderStatus(ordersStatus)
                .deliveryDate(orderInfo.getDeliveryDate())
                .date(TimeUtils.nowDate())
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
     * methodName : checkBookOrderNumberExists<br>
     * author : minsu11<br>
     * description : 주문 번호가 있는지 확인 여부.
     * <br> *
     *
     * @param orderNumber the order number
     * @return the boolean
     */
    public Boolean checkBookOrderNumberExists(String orderNumber) {
        return bookOrderRepository.existBookOrderByOrderNumber(orderNumber);
    }


    /**
     * methodName : checkCouponUsed<br>
     * author : minsu11<br>
     * description : 개별 쿠폰을 사용 여부 체크.
     * <br> *
     *
     * @param orderDetailList 상세 주문의 데이터 목록
     * @return the boolean
     */
    public Boolean checkCouponUsed(List<OrderDetailCreateResponse> orderDetailList) {
        for (OrderDetailCreateResponse orderDetail : orderDetailList) {
            boolean check = orderDetail.getIsCouponUsed();
            if (check) {
                return true;
            }
        }
        return false;
    }


    /**
     * methodName : getBookInfo<br>
     * author : minsu11<br>
     * description : 주문 번호로 주문 조회.
     * <br> *
     *
     * @param orderNumber the order number
     * @return the book info
     */
    public BookOrderInfoPayResponse getBookInfo(String orderNumber) {
        return bookOrderRepository.findBookOrderInfo(orderNumber)
                .orElseThrow(BookOrderNotExistException::new);
    }


    /**
     * methodName : getOrderInfoPayment<br>
     * author : minsu11<br>
     * description : 결제에서 사용할 주문 정보 조회.
     * <br> *
     *
     * @param orderNumber the order number
     * @return the order info payment
     */
    public BookOrderPaymentInfoRespones getOrderInfoPayment(String orderNumber) {
        return bookOrderRepository.findOrderPayInfo(orderNumber)
                .orElseThrow(BookOrderNotExistException::new);
    }


    /**
     * methodName : updateBookOrderStatus<br>
     * author : minsu11<br>
     * description : 주문의 상태 변경.
     * <br> *
     *
     * @param orderNumber the order number
     * @param statusName  the status name
     */
    public void updateBookOrderStatus(String orderNumber, BookOrderStatusName statusName) {
        BookOrder bookOrder = bookOrderRepository.findByNumber(orderNumber)
                .orElseThrow(BookOrderNotExistException::new);
        OrdersStatus ordersStatus = ordersStatusRepository.findById(statusName.toString())
                .orElseThrow(OrdersStatusNotExistException::new);
        bookOrder.updateBookOrderStatus(ordersStatus);

    }

    /**
     * methodName : getUserBookOrderInfo<br>
     * author : minsu11<br>
     * description : 회원의 주문 정보 목록 조회 페이징.
     * <br> *
     *
     * @param pageable the pageable
     * @param userId   the user id
     * @return the user book order info
     */
    public Page<BookOrderUserResponse> getUserBookOrderInfo(Pageable pageable, Long userId) {
        List<BookOrderUserResponse> bookOrderUserList = bookOrderRepository.getUserBookOrderInfos(userId);
        for (int i = 0; i < bookOrderUserList.size(); i++) {
            List<OrderDetailInfoResponse> orderDetailInfoResponses =
                    orderDetailRepository.getOrderDetailList(userId);
            bookOrderUserList.get(i).createOrderDetailInfos(orderDetailInfoResponses);
        }
        Long size = bookOrderRepository.getUserBookOrderCount(userId);
        return new PageImpl<>(bookOrderUserList, pageable, size);
    }

}

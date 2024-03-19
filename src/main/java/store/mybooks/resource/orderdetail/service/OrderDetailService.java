package store.mybooks.resource.orderdetail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.book.entity.Book;
import store.mybooks.resource.book.exception.BookNotExistException;
import store.mybooks.resource.book.repotisory.BookRepository;
import store.mybooks.resource.bookorder.dto.request.BookInfoRequest;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.bookorder.exception.BookOrderNotExistException;
import store.mybooks.resource.bookorder.repository.BookOrderRepository;
import store.mybooks.resource.orderdetail.dto.mapper.OrderDetailMapper;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailCreateResponse;
import store.mybooks.resource.orderdetail.entity.OrderDetail;
import store.mybooks.resource.orderdetail.enumulation.OrderDetailStatusName;
import store.mybooks.resource.orderdetail.repository.OrderDetailRepository;
import store.mybooks.resource.orderdetailstatus.entity.OrderDetailStatus;
import store.mybooks.resource.orderdetailstatus.exception.OrderDetailStatusNotFoundException;
import store.mybooks.resource.orderdetailstatus.repository.OrderDetailStatusRepository;
import store.mybooks.resource.usercoupon.entity.UserCoupon;
import store.mybooks.resource.usercoupon.exception.UserCouponNotExistsException;
import store.mybooks.resource.usercoupon.repository.UserCouponRepository;
import store.mybooks.resource.wrap.entity.Wrap;
import store.mybooks.resource.wrap.exception.WrapNotExistException;
import store.mybooks.resource.wrap.repository.WrapRepository;

/**
 * packageName    : store.mybooks.resource.order_detail.service<br>
 * fileName       : OrderDetailService<br>
 * author         : minsu11<br>
 * date           : 3/16/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/16/24        minsu11       최초 생성<br>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailStatusRepository orderDetailStatusRepository;
    private final BookOrderRepository bookOrderRepository;
    private final UserCouponRepository userCouponRepository;
    private final WrapRepository wrapRepository;
    private final BookRepository bookRepository;
    private final OrderDetailMapper orderDetailMapper;


    /**
     * 상품에서 바로구매 했을 경우 주문 상세 등록.
     *
     * @param request the request
     * @param number  the number
     * @return the order detail create response
     */
    public OrderDetailCreateResponse createOrderDetail(BookInfoRequest request, String number) {
        boolean isCouponUsed = false;
        OrderDetailStatus orderDetailStatus = orderDetailStatusRepository.findById(OrderDetailStatusName.WAIT.name())
                .orElseThrow(OrderDetailStatusNotFoundException::new);

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new BookNotExistException(request.getBookId()));

        BookOrder bookOrder = bookOrderRepository.findByNumber(number)
                .orElseThrow(BookOrderNotExistException::new);
        UserCoupon userCoupon = null;
        if (Objects.nonNull(request.getSelectCouponId())) {

            userCoupon = userCouponRepository.findById(request.getSelectCouponId())
                    .orElseThrow(() -> new UserCouponNotExistsException(request.getSelectCouponId()));
            isCouponUsed = true;
        }
        Wrap wrap = null;
        if (Objects.nonNull(request.getSelectWrapId())) {
            wrap = wrapRepository.findById(request.getSelectWrapId())
                    .orElseThrow(WrapNotExistException::new);
        }

        OrderDetail orderDetail = OrderDetail
                .builder()
                .bookCost(request.getBookCost())
                .amount(request.getAmount())
                .isCouponUsed(isCouponUsed)
                .book(book)
                .bookOrder(bookOrder)
                .detailStatus(orderDetailStatus)
                .userCoupon(userCoupon)
                .wrap(wrap)
                .build();

        return orderDetailMapper.mapToorderDetailCreateResponse(orderDetailRepository.save(orderDetail));
    }

    /**
     * 장바구니를 통한 주문.
     *
     * @param request the request
     * @param number  the number
     * @return the list
     */
    public List<OrderDetailCreateResponse> createOrderDetailList(List<BookInfoRequest> request, String number) {
        List<OrderDetailCreateResponse> orderDetailList = new ArrayList<>();
        for (int i = 0; i < request.size(); i++) {
            OrderDetailCreateResponse response = createOrderDetail(request.get(i), number);
            orderDetailList.add(response);
        }
        return orderDetailList;
    }


}


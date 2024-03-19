package store.mybooks.resource.bookorder.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.bookorder.dto.response.BookOrderInfoPayResponse;
import store.mybooks.resource.bookorder.dto.response.BookOrderPaymentInfoRespones;
import store.mybooks.resource.bookorder.dto.response.BookOrderUserResponse;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminResponse;
import store.mybooks.resource.bookorder.entity.BookOrder;
import store.mybooks.resource.bookorder.entity.QBookOrder;
import store.mybooks.resource.image.entity.QImage;
import store.mybooks.resource.orderdetail.dto.response.OrderDetailInfoResponse;
import store.mybooks.resource.orderdetail.entity.QOrderDetail;
import store.mybooks.resource.ordersstatus.enumulation.OrdersStatusEnum;

/**
 * packageName    : store.mybooks.resource.book_order.repository<br>
 * fileName       : BookOrderRepositoryImpl<br>
 * author         : minsu11<br>
 * date           : 3/2/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/2/24        minsu11       최초 생성<br>
 */
public class BookOrderRepositoryImpl extends QuerydslRepositorySupport implements BookOrderRepositoryCustom {
    private static final QBookOrder bookOrder = QBookOrder.bookOrder;

    public BookOrderRepositoryImpl() {
        super(BookOrder.class);
    }


    @Override
    public Page<BookOrderUserResponse> getBookOrderPageByUserId(Long userId, Pageable pageable) {

        List<BookOrderUserResponse> bookOrderResponseList =
                from(bookOrder)
                        .select(Projections.constructor(BookOrderUserResponse.class,
                                bookOrder.user.id,
                                bookOrder.orderStatus.id,
                                bookOrder.deliveryRule.id,
                                bookOrder.date,
                                bookOrder.invoiceNumber,
                                bookOrder.receiverName,
                                bookOrder.receiverAddress,
                                bookOrder.receiverPhoneNumber,
                                bookOrder.receiverMessage,
                                bookOrder.totalCost,
                                bookOrder.pointCost,
                                bookOrder.couponCost,
                                bookOrder.number,
                                bookOrder.findPassword))
                        .where(bookOrder.user.id.eq(userId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long count = from(bookOrder).fetchCount();

        return new PageImpl<>(bookOrderResponseList, pageable, count);
    }

    @Override
    public Page<BookOrderAdminResponse> getBookOrderPageByOrderStatusId(Pageable pageable) {
        List<BookOrderAdminResponse> bookOrderAdminResponseList =
                from(bookOrder)
                        .select(Projections.constructor(BookOrderAdminResponse.class,
                                bookOrder.id,
                                bookOrder.user.id,
                                bookOrder.orderStatus.id,
                                bookOrder.date,
                                bookOrder.outDate,
                                bookOrder.invoiceNumber,
                                bookOrder.number))
                        .where(bookOrder.orderStatus.id.eq(OrdersStatusEnum.WAIT.toString()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
        long count = bookOrderAdminResponseList.size();

        return new PageImpl<>(bookOrderAdminResponseList, pageable, count);
    }

    @Override
    public Boolean existBookOrderByOrderNumber(String orderNumber) {

        return Objects.nonNull(
                from(bookOrder)
                        .where(bookOrder.number.eq(orderNumber))
        );
    }

    @Override
    public Optional<BookOrderInfoPayResponse> findBookOrderInfo(String orderNumber) {
        QOrderDetail orderDetail = QOrderDetail.orderDetail;

        List<OrderDetailInfoResponse> orderDetailInfoResponses = from(orderDetail)
                .select(Projections.constructor(
                                OrderDetailInfoResponse.class,
                                orderDetail.book.name,
                                orderDetail.bookCost,
                                orderDetail.isCouponUsed
                        )
                )
                .where(orderDetail.bookOrder.number.eq(orderNumber))
                .fetch();
        BookOrderInfoPayResponse bookorderInfo =
                from(bookOrder)
                        .select(Projections.constructor(BookOrderInfoPayResponse.class,
                                bookOrder.orderStatus.id,
                                bookOrder.number,
                                bookOrder.totalCost))
                        .where(bookOrder.number.eq(orderNumber))
                        .fetchOne();
        bookorderInfo.setOrderDetails(orderDetailInfoResponses);

        return Optional.of(bookorderInfo
        );
    }

    @Override
    public Optional<BookOrderPaymentInfoRespones> findOrderPayInfo(String orderNumber) {
        QOrderDetail orderDetail = QOrderDetail.orderDetail;
        BookOrderPaymentInfoRespones bookOrderInfoPayResponse = from(bookOrder)
                .select(Projections.constructor(
                        BookOrderPaymentInfoRespones.class,
                        bookOrder.user.name,
                        bookOrder.user.email,
                        bookOrder.user.phoneNumber,
                        bookOrder.number,
                        bookOrder.orderStatus.id
                ))
                .where(bookOrder.number.eq(orderNumber))
                .fetchOne();

        Long count = from(orderDetail)
                .where(orderDetail.bookOrder.number.eq(orderNumber))
                .fetchCount();

        StringBuilder orderName = new StringBuilder().append(from(orderDetail)
                .select(orderDetail.book.name)
                .where(orderDetail.bookOrder.number.eq(orderNumber))
                .fetchFirst());

        if (count > 1) {
            count -= 1;
            orderName.append("외 " + count + "건");
        }
        bookOrderInfoPayResponse.updateOrderName(orderName.toString());

        return Optional.of(
                bookOrderInfoPayResponse
        );
    }

    @Override
    public Long getUserBookOrderCount(Long userId) {

        return from(bookOrder)
                .where(bookOrder.user.id.eq(userId))
                .fetchCount();
    }

    @Override
    public List<BookOrderUserResponse> getUserBookOrderInfos(Long userId) {
        QImage image = QImage.image;
        QOrderDetail orderDetail = QOrderDetail.orderDetail;
        return from(orderDetail)
                .join(image)
                .on(image.book.eq(orderDetail.book))
                .join(bookOrder)
                .on(bookOrder.eq(orderDetail.bookOrder))
                .select(Projections.constructor(
                        BookOrderUserResponse.class,
                        bookOrder.orderStatus.id,
                        bookOrder.deliveryRule.deliveryRuleName.id,
                        bookOrder.deliveryRule.cost,
                        bookOrder.date,
                        bookOrder.invoiceNumber,
                        bookOrder.receiverName,
                        bookOrder.receiverAddress,
                        bookOrder.receiverPhoneNumber,
                        bookOrder.receiverMessage,
                        bookOrder.totalCost,
                        bookOrder.pointCost,
                        bookOrder.couponCost,
                        bookOrder.number,
                        image.path.concat(image.fileName).concat(image.extension)

                )).where(bookOrder.user.id.eq(userId))
                .fetch();
    }


}

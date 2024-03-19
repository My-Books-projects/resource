package store.mybooks.resource.bookorder.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.mybooks.resource.bookorder.dto.response.BookOrderInfoPayResponse;
import store.mybooks.resource.bookorder.dto.response.BookOrderPaymentInfoRespones;
import store.mybooks.resource.bookorder.dto.response.BookOrderUserResponse;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminResponse;

/**
 * packageName    : store.mybooks.resource.book_order.repository<br>
 * fileName       : BookOrderRepositoryCustom<br>
 * author         : minsu11<br>
 * date           : 3/2/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/2/24        minsu11       최초 생성<br>
 */
@NoRepositoryBean
public interface BookOrderRepositoryCustom {
    Page<BookOrderUserResponse> getBookOrderPageByUserId(Long userId, Pageable pageable);

    Page<BookOrderAdminResponse> getBookOrderPageByOrderStatusId(Pageable pageable);

    Boolean existBookOrderByOrderNumber(String orderNumber);

    Optional<BookOrderInfoPayResponse> findBookOrderInfo(String orderNumber);

    Optional<BookOrderPaymentInfoRespones> findOrderPayInfo(String orderNumber);

    List<BookOrderUserResponse> getUserBookOrderInfos(Long userId);

    Long getUserBookOrderCount(Long userId);

}

package store.mybooks.resource.bookorder.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import store.mybooks.resource.bookorder.dto.response.BookOrderCreateResponse;
import store.mybooks.resource.bookorder.dto.response.BookOrderRegisterInvoiceResponse;
import store.mybooks.resource.bookorder.dto.response.admin.BookOrderAdminModifyResponse;
import store.mybooks.resource.bookorder.entity.BookOrder;

/**
 * packageName    : store.mybooks.resource.book_order.dto.mapper<br>
 * fileName       : BookOrderMapper<br>
 * author         : minsu11<br>
 * date           : 3/2/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/2/24        minsu11       최초 생성<br>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BookOrderMapper {
    @Mapping(source = "orderStatus.id", target = "statusId")
    BookOrderAdminModifyResponse mapToBookOrderModifyOrderStatusResponse(BookOrder bookOrder);

    BookOrderRegisterInvoiceResponse mapToBookOrderRegisterInvoiceResponse(BookOrder bookOrder);

    @Mapping(source = "orderStatus.id", target = "orderStatus")
    BookOrderCreateResponse mapToBookOrderCreateResponse(BookOrder bookOrder);
}

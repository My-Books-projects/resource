package store.mybooks.resource.pointhistory.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import store.mybooks.resource.pointhistory.dto.response.PointHistoryCreateResponse;
import store.mybooks.resource.pointhistory.entity.PointHistory;

/**
 * packageName    : store.mybooks.resource.pointhistory.dto.mapper<br>
 * fileName       : PointHistoryMapper<br>
 * author         : minsu11<br>
 * date           : 3/19/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/19/24        minsu11       최초 생성<br>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PointHistoryMapper {
    @Mapping(source = "pointRule.id", target = "pointId")
    PointHistoryCreateResponse mapToPointHistoryCreateResponse(PointHistory pointHistory);

}

package store.mybooks.resource.pointhistory.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.pointhistory.dto.response.PointHistoryResponse;
import store.mybooks.resource.pointhistory.dto.response.PointResponse;
import store.mybooks.resource.pointhistory.entity.PointHistory;
import store.mybooks.resource.pointhistory.entity.QPointHistory;

/**
 * packageName    : store.mybooks.resource.point_history.repository<br>
 * fileName       : PointHistoryRepositoryImpl<br>
 * author         : minsu11<br>
 * date           : 3/7/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/7/24        minsu11       최초 생성<br>
 */
@Slf4j
public class PointHistoryRepositoryImpl extends QuerydslRepositorySupport implements PointHistoryRepositoryCustom {
    private static final QPointHistory pointHistory = QPointHistory.pointHistory;

    public PointHistoryRepositoryImpl() {
        super(PointHistory.class);
    }

    @Override
    public PointResponse getRemainingPoint(Long userId) {
        return from(pointHistory)
                .select(Projections.constructor(PointResponse.class, pointHistory.pointStatusCost.sum()))
                .where(pointHistory.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public Page<PointHistoryResponse> getPointHistoryByUserId(Pageable pageable, Long userId) {
        List<PointHistoryResponse> pointHistoryResponses =
                from(pointHistory)
                        .where(pointHistory.user.id.eq(userId))
                        .orderBy(pointHistory.createdDate.desc())
                        .select(Projections.constructor(PointHistoryResponse.class,
                                pointHistory.pointRule.pointRuleName.id,
                                pointHistory.pointStatusCost,
                                pointHistory.createdDate))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total = from(pointHistory)
                .where(pointHistory.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(pointHistoryResponses, pageable, total);
    }
}

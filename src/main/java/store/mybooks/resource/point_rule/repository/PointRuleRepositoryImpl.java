package store.mybooks.resource.point_rule.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.point_rule.dto.response.PointRuleResponse;
import store.mybooks.resource.point_rule.entity.QPointRule;

/**
 * packageName    : store.mybooks.resource.point_rule.repository<br>
 * fileName       : PointRuleRepositoryImpl<br>
 * author         : minsu11<br>
 * date           : 3/7/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/7/24        minsu11       최초 생성<br>
 */
public class PointRuleRepositoryImpl extends QuerydslRepositorySupport implements PointRuleRepositoryCustom {

    private static final QPointRule pointRule = QPointRule.pointRule;

    public PointRuleRepositoryImpl() {
        super(PointRuleResponse.class);
    }


    @Override
    public Optional<PointRuleResponse> getPointRuleById(Integer id) {
        return Optional.of(from(pointRule)
                .select(Projections.constructor(
                        PointRuleResponse.class,
                        pointRule.id,
                        pointRule.pointRuleName.id,
                        pointRule.rate,
                        pointRule.cost))
                .where(pointRule.isAvailable.eq(true))
                .fetchOne());
    }

    @Override
    public List<PointRuleResponse> getPointRuleList() {
        return from(pointRule)
                .select(Projections.constructor(PointRuleResponse.class,
                        pointRule.id,
                        pointRule.pointRuleName.id,
                        pointRule.rate,
                        pointRule.cost))
                .where(pointRule.isAvailable.eq(true))
                .fetch();
    }
}

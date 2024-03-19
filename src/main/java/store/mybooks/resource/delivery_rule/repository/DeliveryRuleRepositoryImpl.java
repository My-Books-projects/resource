package store.mybooks.resource.delivery_rule.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import store.mybooks.resource.delivery_rule.dto.response.DeliveryRuleResponse;
import store.mybooks.resource.delivery_rule.entity.DeliveryRule;
import store.mybooks.resource.delivery_rule.entity.QDeliveryRule;

/**
 * packageName    : store.mybooks.resource.delivery_rule.repository <br/>
 * fileName       : DeliveryRuleRepositoryImpl<br/>
 * author         : Fiat_lux <br/>
 * date           : 3/11/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 3/11/24        Fiat_lux       최초 생성<br/>
 */
public class DeliveryRuleRepositoryImpl extends QuerydslRepositorySupport implements DeliveryRuleRepositoryCustom {
    public DeliveryRuleRepositoryImpl() {
        super(DeliveryRule.class);
    }

    @Override
    public List<DeliveryRuleResponse> getDeliveryRuleList() {
        QDeliveryRule deliveryRule = QDeliveryRule.deliveryRule;

        return from(deliveryRule)
                .select(
                        Projections.constructor(
                                DeliveryRuleResponse.class,
                                deliveryRule.id,
                                deliveryRule.deliveryRuleName.id,
                                deliveryRule.companyName,
                                deliveryRule.cost,
                                deliveryRule.ruleCost,
                                deliveryRule.createdDate,
                                deliveryRule.isAvailable)
                ).fetch();
    }

    @Override
    public Optional<DeliveryRuleResponse> getDeliveryRuleByName(String name) {
        QDeliveryRule deliveryRule = QDeliveryRule.deliveryRule;

        return Optional.ofNullable(
                from(deliveryRule)
                        .select(Projections.constructor(
                                DeliveryRuleResponse.class,
                                deliveryRule.id,
                                deliveryRule.deliveryRuleName.id,
                                deliveryRule.companyName,
                                deliveryRule.cost,
                                deliveryRule.ruleCost,
                                deliveryRule.createdDate,
                                deliveryRule.isAvailable
                        ))
                        .where(deliveryRule.deliveryRuleName.id.eq(name))
                        .fetchOne()
        );
    }
}

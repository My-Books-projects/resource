package store.mybooks.resource.delivery_rule.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import store.mybooks.resource.delivery_rule.dto.response.DeliveryRuleResponse;

/**
 * packageName    : store.mybooks.resource.delivery_rule.repository <br/>
 * fileName       : DeliveryRuleRepositoryCustom<br/>
 * author         : Fiat_lux <br/>
 * date           : 3/11/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 3/11/24        Fiat_lux       최초 생성<br/>
 */
@NoRepositoryBean
public interface DeliveryRuleRepositoryCustom {
    List<DeliveryRuleResponse> getDeliveryRuleList();

    Optional<DeliveryRuleResponse> getDeliveryRuleByName(String name);
}

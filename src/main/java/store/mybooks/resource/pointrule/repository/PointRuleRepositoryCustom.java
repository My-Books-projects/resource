package store.mybooks.resource.pointrule.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import store.mybooks.resource.pointrule.dto.response.PointRuleResponse;
import store.mybooks.resource.pointrule.entity.PointRule;

/**
 * packageName    : store.mybooks.resource.point_rule.repository<br>
 * fileName       : PointRuleRepositoryCustom<br>
 * author         : minsu11<br>
 * date           : 3/7/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/7/24        minsu11       최초 생성<br>
 */
@NoRepositoryBean
public interface PointRuleRepositoryCustom {
    /**
     * methodName : getPointRuleById<br>
     * author : minsu11<br>
     * description : id로 포인트 규정 조회.
     * <br> *
     *
     * @param id 조회할 포인트 규정 아이디
     * @return optional
     */
    Optional<PointRuleResponse> getPointRuleById(Integer id);

    /**
     * methodName : getPointRuleList<br>
     * author : minsu11<br>
     * description : 포인트 규정 전체 조회.
     * <br> *
     *
     * @return list
     */
    List<PointRuleResponse> getPointRuleList();

    /**
     * methodName : findPointRuleByPointRuleName<br>
     * author : minsu11<br>
     * description : 포인트 규정 명으로 사용 중인 포인트 규정 조회.
     * <br> *
     *
     * @param pointRuleName 포인트 규정 명
     * @return point rule
     */
    Optional<PointRule> findPointRuleByPointRuleName(String pointRuleName);

    /**
     * methodName : getPointRuleResponsePage<br>
     * author : minsu11<br>
     * description : 포인트 규정 페이징.
     * <br> *
     *
     * @param pageable 페이징
     * @return page
     */
    Page<PointRuleResponse> getPointRuleResponsePage(Pageable pageable);


}

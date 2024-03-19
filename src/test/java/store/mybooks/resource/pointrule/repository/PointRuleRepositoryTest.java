package store.mybooks.resource.pointrule.repository;

import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import store.mybooks.resource.pointrule.dto.response.PointRuleResponse;
import store.mybooks.resource.pointrule.entity.PointRule;
import store.mybooks.resource.pointrulename.entity.PointRuleName;
import store.mybooks.resource.pointrulename.repository.PointRuleNameRepository;

/**
 * packageName    : store.mybooks.resource.point_rule.repository<br>
 * fileName       : PointRuleRepositoryTest<br>
 * author         : minsu11<br>
 * date           : 3/9/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/9/24        minsu11       최초 생성<br>
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class PointRuleRepositoryTest {
    @Autowired
    private PointRuleRepository pointRuleRepository;

    @Autowired
    private PointRuleNameRepository pointRuleNameRepository;

    @BeforeEach
    void setUp() {
        PointRuleName pointRuleName1 = new PointRuleName("test1");
        PointRuleName pointRuleName2 = new PointRuleName("test2");
        pointRuleNameRepository.save(pointRuleName1);
        pointRuleNameRepository.save(pointRuleName2);

        PointRule pointRule1 = new PointRule(1, pointRuleName1, 10, null, LocalDate.of(1212, 12, 12), true);
        PointRule pointRule2 = new PointRule(2, pointRuleName2, null, 10, LocalDate.of(1212, 12, 12), false);
        pointRuleRepository.save(pointRule1);
        pointRuleRepository.save(pointRule2);
    }

    @Test
    @Order(1)
    @DisplayName("id에 맞는 포인트 규정 조회")
    void givenId_whenGetPointRuleById_thenReturnPointRuleResponseOptional() {
        PointRuleResponse actual = pointRuleRepository.getPointRuleById(1).orElse(null);
        assert actual != null;
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("test1", actual.getPointRuleName());
        Assertions.assertEquals(10, actual.getRate());

    }

    @Test
    @Order(2)
    @DisplayName("포인트 규정 목록 조회")
    void whenGetPointRuleList_thenReturnPointRuleResponseList() {
        List<PointRuleResponse> expected = List.of(new PointRuleResponse(3, "test1", 10, null));
        List<PointRuleResponse> actual = pointRuleRepository.getPointRuleList();
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getCost(), actual.get(0).getCost());
    }

    @Test
    @Order(3)
    @DisplayName("포인트 규정 명으로 포인트 규정 찾기")
    void givenPointRuleName_whenFindPointRuleByPointRuleName_thenReturnPointRule() {
        PointRuleName pointRuleName = new PointRuleName("test1");
        PointRule expected = new PointRule(1, pointRuleName, 10, null, LocalDate.of(1212, 12, 12), true);
        PointRule actual = pointRuleRepository.findPointRuleByPointRuleName("test1").orElse(null);

        Assertions.assertEquals(expected.getRate(), actual.getRate());
    }

    @Test
    @Order(4)
    @DisplayName("포인트 규정 페이징 테스트")
    void givenPageable_whenGetPointResponsePage_thenReturnPointRuleResponseList() {
        Pageable pageable = PageRequest.of(0, 2);
        List<PointRuleResponse> expected = List.of(new PointRuleResponse(3, "test1", 10, null));
        List<PointRuleResponse> actual = pointRuleRepository.getPointRuleResponsePage(pageable).getContent();

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getPointRuleName(), actual.get(0).getPointRuleName());

    }


}
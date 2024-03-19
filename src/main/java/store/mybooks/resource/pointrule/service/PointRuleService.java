package store.mybooks.resource.pointrule.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.pointrule.dto.mapper.PointRuleMapper;
import store.mybooks.resource.pointrule.dto.request.PointRuleCreateRequest;
import store.mybooks.resource.pointrule.dto.request.PointRuleModifyRequest;
import store.mybooks.resource.pointrule.dto.response.PointRuleCreateResponse;
import store.mybooks.resource.pointrule.dto.response.PointRuleModifyResponse;
import store.mybooks.resource.pointrule.dto.response.PointRuleResponse;
import store.mybooks.resource.pointrule.entity.PointRule;
import store.mybooks.resource.pointrule.exception.PointRuleNotExistException;
import store.mybooks.resource.pointrule.repository.PointRuleRepository;
import store.mybooks.resource.pointrulename.entity.PointRuleName;
import store.mybooks.resource.pointrulename.exception.PointRuleNameNotExistException;
import store.mybooks.resource.pointrulename.repository.PointRuleNameRepository;

/**
 * packageName    : store.mybooks.resource.point_rule.service<br>
 * fileName       : PointRuleService<br>
 * author         : minsu11<br>
 * date           : 3/7/24<br>
 * description    : 포인트 규정에 관한 로직 처리하는 {@code service}.
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 3/7/24        minsu11       최초 생성<br>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PointRuleService {
    private final PointRuleRepository pointRuleRepository;
    private final PointRuleNameRepository pointRuleNameRepository;
    private final PointRuleMapper pointRuleMapper;


    /**
     * methodName : getPointRuleResponse<br>
     * author : minsu11<br>
     * description : {@code id}로 포인트 규정 조회.
     * <br> *
     *
     * @param id 조회할 포인트 규정 {@code id}
     * @return point rule response
     */
    @Transactional(readOnly = true)
    public PointRuleResponse getPointRuleResponse(Integer id) {
        return pointRuleRepository.getPointRuleById(id)
                .orElseThrow(PointRuleNotExistException::new);
    }

    /**
     * methodName : getPointRuleList<br>
     * author : minsu11<br>
     * description : 전체 포인트 규정 조회.
     * <br> *
     *
     * @return list
     */
    @Transactional(readOnly = true)
    public List<PointRuleResponse> getPointRuleList() {
        return pointRuleRepository.getPointRuleList();
    }

    /**
     * methodName : getPointRuleResponsePage<br>
     * author : minsu11<br>
     * description : 포인트 규정 페이징.
     * <br> *
     *
     * @param pageable 페이징
     * @return page
     */
    @Transactional(readOnly = true)
    public Page<PointRuleResponse> getPointRuleResponsePage(Pageable pageable) {
        return pointRuleRepository.getPointRuleResponsePage(pageable);
    }

    /**
     * methodName : createPointRuleResponse<br>
     * author : minsu11<br>
     * description : 포인트 규정 등록.
     * <br> *
     *
     * @param request 등록할 포인트 규정
     * @return point rule create response
     */
    public PointRuleCreateResponse createPointRuleResponse(PointRuleCreateRequest request) {
        String name = request.getPointRuleName();
        PointRuleName pointRuleName = pointRuleNameRepository.findById(request.getPointRuleName())
                .orElseThrow(PointRuleNameNotExistException::new);
        PointRule pointRule = new PointRule(pointRuleName, request.getRate(), request.getCost());

        PointRule beforePointRule = pointRuleRepository.findPointRuleByPointRuleName(name)
                .orElse(null);
        if (Objects.nonNull(beforePointRule)) {
            beforePointRule.modifyPointRuleIsAvailable(false);
        }

        return pointRuleMapper.mapToPointRuleCreateResponse(pointRuleRepository.save(pointRule));
    }

    /**
     * methodName : modifyPointRuleResponse<br>
     * author : minsu11<br>
     * description : 포인트 규정 수정. 포인트 규정과 포인트 규정 명에 저장되지 않은 값이 들어오면
     * {@code NotExistException}을 던짐
     * <br> *
     *
     * @param request 수정할 포인트 규정
     * @param id      수정 할 포인트 규정 아이디
     * @return point rule modify response
     * @throws PointRuleNotExistException     포인트 규정을 찾지 못한 경우
     * @throws PointRuleNameNotExistException 포인트 규정 명을 찾지 못한 경우
     */
    public PointRuleModifyResponse modifyPointRuleResponse(PointRuleModifyRequest request, Integer id) {

        PointRule beforePointRule = pointRuleRepository.findById(id).orElseThrow(PointRuleNotExistException::new);
        String name = request.getPointRuleName();
        PointRuleName pointRuleName = pointRuleNameRepository.findById(name)
                .orElseThrow(PointRuleNameNotExistException::new);

        PointRule pointRule = new PointRule(pointRuleName, request.getRate(), request.getCost());

        beforePointRule.modifyPointRuleIsAvailable(false);

        return pointRuleMapper.mapToPointRuleModifyResponse(pointRuleRepository.save(pointRule));
    }

    /**
     * methodName : deletePointRuleResponse<br>
     * author : minsu11<br>
     * description : 포인트 규정 삭제. 실제 삭제가 아닌 사용 중 상태를 {@code false}를 하여 약 삭제.
     * <br> *
     *
     * @param id 삭제할 포인트 규정 아이디
     */
    public void deletePointRuleResponse(Integer id) {
        PointRule pointRule = pointRuleRepository.findById(id).orElseThrow(PointRuleNotExistException::new);
        pointRule.modifyPointRuleIsAvailable(false);
    }

}

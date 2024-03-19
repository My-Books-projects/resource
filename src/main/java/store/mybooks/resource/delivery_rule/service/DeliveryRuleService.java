package store.mybooks.resource.delivery_rule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.mybooks.resource.delivery_rule.dto.mapper.DeliveryRuleMapper;
import store.mybooks.resource.delivery_rule.dto.request.DeliveryRuleModifyRequest;
import store.mybooks.resource.delivery_rule.dto.request.DeliveryRuleRegisterRequest;
import store.mybooks.resource.delivery_rule.dto.response.DeliveryRuleResponse;
import store.mybooks.resource.delivery_rule.entity.DeliveryRule;
import store.mybooks.resource.delivery_rule.exception.DeliveryRuleNotExistsException;
import store.mybooks.resource.delivery_rule.repository.DeliveryRuleRepository;
import store.mybooks.resource.delivery_rule_name.entity.DeliveryRuleName;
import store.mybooks.resource.delivery_rule_name.exception.DeliveryRuleNameNotExistsException;
import store.mybooks.resource.delivery_rule_name.repository.DeliveryRuleNameRepository;

/**
 * packageName    : store.mybooks.resource.delivery_rule.service<br>
 * fileName       : DeliveryRuleService<br>
 * author         : Fiat_lux<br>
 * date           : 2/15/24<br>
 * description    : 배송 규칙 등록, 수정, 삭제, 조회를 담당하는 서비스 <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/15/24        Fiat_lux       최초 생성<br>
 */
@Service
@RequiredArgsConstructor
public class DeliveryRuleService {

    private final DeliveryRuleRepository deliveryRuleRepository;
    private final DeliveryRuleNameRepository deliveryRuleNameRepository;
    private final DeliveryRuleMapper deliveryRuleMapper;

    /**
     * methodName : registerDeliveryRule<br>
     * author : Fiat_lux<br>
     * description : 배송 규칙 등록 요청이 들어 왔을 때 {@code deliveryRuleRegisterRequest}의 정보를 저장<br>
     * 저장 하기 전 {@code deliveryRuleRegisterRequest}의 {@code id}으로 데이터를 조회<br>
     * 조회 데이터가 없으면 {@code DeliveryRuleNameNotFoundException}를 던짐<br>
     * <br>
     *
     * @param deliveryRuleRegisterRequest the delivery rule register request
     * @return the delivery rule response
     */
    @Transactional
    public DeliveryRuleResponse registerDeliveryRule(DeliveryRuleRegisterRequest deliveryRuleRegisterRequest) {

        DeliveryRuleName deliveryRuleName =
                deliveryRuleNameRepository.findById(deliveryRuleRegisterRequest.getDeliveryNameRuleId())
                        .orElseThrow(() -> new DeliveryRuleNameNotExistsException("배송 이름 규칙이 존재하지 않습니다."));

        DeliveryRule deliveryRule = new DeliveryRule(deliveryRuleName,
                deliveryRuleRegisterRequest.getDeliveryRuleCompanyName(),
                deliveryRuleRegisterRequest.getDeliveryCost(),
                deliveryRuleRegisterRequest.getDeliveryRuleCost());

        DeliveryRule saveDeliveryRule = deliveryRuleRepository.save(deliveryRule);
        return deliveryRuleMapper.mapToResponse(saveDeliveryRule);

    }

    /**
     * Gets delivery rule list.
     *
     * @return the delivery rule list
     */
    public List<DeliveryRuleResponse> getDeliveryRuleList() {
        return deliveryRuleRepository.getDeliveryRuleList();
    }

    /**
     * methodName : getDeliveryRule<br>
     * author : Fiat_lux<br>
     * description : 배송 규칙 데이터를 조회<br>
     * {@code id} 의 데이터 조회 <br>
     * {@code id}로 배송 규칙 조회 할 수 없는 경우 {@code DeliveryRuleNotFoundException} 던짐<br>
     *
     * @param id the id
     * @return the delivery rule
     * @throws DeliveryRuleNotExistsException {@code id}의 데이터 조회 할 수 없는 경우 던짐
     */
    @Transactional(readOnly = true)
    public DeliveryRuleResponse getDeliveryRule(Integer id) {

        DeliveryRule deliveryRule = deliveryRuleRepository.findById(id)
                .orElseThrow(() -> new DeliveryRuleNotExistsException("배송 규칙이 존재하지 않습니다"));
        return deliveryRuleMapper.mapToResponse(deliveryRule);
    }

    /**
     * methodName : modifyDeliveryRule<br>
     * author : Fiat_lux<br>
     * description : {@code id}에 따른 배송규칙을 조회 한 후 <br>
     * 조회 한 데이터를 {@code deliveryRuleModifyRequest} 데이터로 수정<br>
     * 배송 규칙 이름, 배송 규칙  조회 시 데이터가 존재하지 않을 경우<br>
     * {@code DeliveryRuleNameNotFoundException}, {@code DeliveryRuleNotFoundException}을 던짐<br>
     *
     * @param deliveryRuleModifyRequest the delivery rule modify request
     * @return the delivery rule response
     * @throws DeliveryRuleNotExistsException     배송 규칙이 존재하지 않을 경우
     * @throws DeliveryRuleNameNotExistsException 배송 규칙 이름이 존재하지 않을 경우
     */
    @Transactional
    public DeliveryRuleResponse modifyDeliveryRule(DeliveryRuleModifyRequest deliveryRuleModifyRequest) {
        DeliveryRule deliveryRule = deliveryRuleRepository.findById(deliveryRuleModifyRequest.getId())
                .orElseThrow(() -> new DeliveryRuleNotExistsException("배송 규칙이 존재하지 않습니다."));

        deliveryRule.setCompanyName(deliveryRuleModifyRequest.getDeliveryRuleCompanyName());
        deliveryRule.setCost(deliveryRuleModifyRequest.getDeliveryCost());
        deliveryRule.setRuleCost(deliveryRuleModifyRequest.getDeliveryRuleCost());
        return deliveryRuleMapper.mapToResponse(deliveryRule);
    }


    /**
     * methodName : deleteDeliveryRule<br>
     * author : Fiat_lux<br>
     * description : 배송 규칙 데이터 삭제<br>
     * {@code id}의 배송 규칙 조회<br>
     * {@code id}로 배송 규칙 조회 할 수 없는 경우 {@code DeliveryRuleNotFoundException} 던짐<br>
     *
     * @param id the id
     * @throws DeliveryRuleNotExistsException {@code id}의 데이터를 조회 할 수 없는 경우
     */
    @Transactional
    public void deleteDeliveryRule(Integer id) {
        DeliveryRule deliveryRule = deliveryRuleRepository.findById(id)
                .orElseThrow(() -> new DeliveryRuleNotExistsException("배송 규칙이 존재하지 않습니다."));
        if (deliveryRule.getIsAvailable() == 1) {
            deliveryRule.setIsAvailable(0);
        }
    }

    /**
     * methodName : deleteDeliveryRule<br>
     * author : Fiat_lux<br>
     * description : {@code name}으로 배송 규정 조회.<br>
     * {@code name}로 배송 규칙 조회 할 수 없는 경우 {@code DeliveryRuleNotFoundException} 던짐.<br>
     *
     * @param name the name
     * @return the delivery rule by name
     * @throws DeliveryRuleNotExistsException 조회할 배송 규정이 없는 경우
     */
    @Transactional(readOnly = true)
    public DeliveryRuleResponse getDeliveryRuleByName(String name) {
        return deliveryRuleRepository.getDeliveryRuleByName(name)
                .orElseThrow(() -> new DeliveryRuleNotExistsException("존재 하지 않는 배송 규정입니다."));
    }

}

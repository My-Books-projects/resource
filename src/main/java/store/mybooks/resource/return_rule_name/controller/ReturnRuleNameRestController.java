package store.mybooks.resource.return_rule_name.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.mybooks.resource.return_rule_name.dto.response.ReturnRuleNameResponse;
import store.mybooks.resource.return_rule_name.service.ReturnRuleNameService;

/**
 * packageName    : store.mybooks.resource.return_name_rule.controller<br>
 * fileName       : ReturnNameRuleController<br>
 * author         : minsu11<br>
 * date           : 2/20/24<br>
 * description    :
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 2/20/24        minsu11       최초 생성<br>
 */
@RestController
@RequestMapping("/api/return-name-rule")
@RequiredArgsConstructor
public class ReturnRuleNameRestController {
    private final ReturnRuleNameService returnRuleNameService;

    /**
     * methodName : getReturnNameRule<br>
     * author : minsu11<br>
     * description : get 요청 시 name 값에 맞는 데이터를 응답
     *
     * @param id url 경로로 들어온 요청 데이터
     * @return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReturnRuleNameResponse> getReturnRuleName(@PathVariable(name = "id") String id) {
        ReturnRuleNameResponse response = returnRuleNameService.getReturnRuleName(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * methodName : getReturnRuleNameList<br>
     * author : minsu11<br>
     * description : 요청 시 모든 반품 규정 명을 DTO List 응답
     *
     * @return the return rule name list
     */
    @GetMapping
    public ResponseEntity<List<ReturnRuleNameResponse>> getReturnRuleNameList() {
        List<ReturnRuleNameResponse> returnRuleNameResponseList = returnRuleNameService.getReturnRuleNameList();
        return new ResponseEntity<>(returnRuleNameResponseList, HttpStatus.OK);
    }


}

package store.mybooks.resource.delivery_rule.exception;

import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;

/**
 * packageName    : store.mybooks.resource.delivery_rule.exception <br/>
 * fileName       : DeliveryRuleValidationFailedException<br/>
 * author         : Fiat_lux <br/>
 * date           : 2/26/24<br/>
 * description    :<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2/26/24        Fiat_lux       최초 생성<br/>
 */
public class DeliveryRuleValidationFailedException extends RuntimeException {
    public DeliveryRuleValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
                .stream()
                .map(objectError -> new StringBuilder()
                        .append("object: ")
                        .append(objectError.getObjectName())
                        .append(", message: ")
                        .append(objectError.getDefaultMessage())
                        .append(", error code: ")
                        .append(objectError.getCode()))
                .collect(Collectors.joining("|")));
    }
}

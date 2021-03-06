package toy.jinseokshop.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import toy.jinseokshop.domain.item.ItemConst;
import toy.jinseokshop.web.order.ItemDetailOrderDto;

@Component
public class OrderValidator {

    // 정수형이 아닌 실수형 (가령 1.3)으로 입력 시 스프링에서 이미 bindingResult에 에러를 바인딩 해주므로, 이 곳에서는 음수인 정수만 검출해주면 될 듯 하다.
    public void BookOrderValidate(ItemDetailOrderDto orderDto, BindingResult bindingResult) {
        // 위의 주석처럼, 이곳에 넘어오는 orderQuantity는 정수다. 즉 음의정수, 0, 양의정수
        // 갯수 0을 주문했다는 것은 의미가 없으니, 음의정수와 0을 에러로 치환해야 한다.
        // 2022. 1. 23 - 십만 이상의 값으로 주문을 한 사용자가 있었음... 시스템이 이상하게 작동해서 주문값이 -로 들어갔다.
        // 한 번 구매시 50개로 제한해야겠음!
        if (orderDto.getCategory().equals("책")) {
            if (orderDto.getOrderItemQuantity() <= 0 || orderDto.getOrderItemQuantity() > 50) {
                bindingResult.addError(new ObjectError("quantityError", "상품은 1 이상 50 이하의 정수만 입력 가능합니다."));
            }
        }
    }

}

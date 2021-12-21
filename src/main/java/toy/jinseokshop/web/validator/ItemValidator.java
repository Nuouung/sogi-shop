package toy.jinseokshop.web.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import toy.jinseokshop.domain.item.Item;

@Component
@Slf4j
public class ItemValidator {

    public void writeValidate(Item item, BindingResult bindingResult) {

        // 1. 가격은 0원일 수 없다. 가격은 빈 값일 수 없다. 가격은 1,000,000원을 초과할 수 없다.
        // 2. 수량은 5개 이상 1,000개 이하여야 한다.
        // 3. 아이템 이름에는 빈 값이 들어갈 수 없다.
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "이름에는 빈 값이 들어갈 수 없습니다."));
        }

        if (item.getPrice() == null || item.getPrice() <= 0 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "상품의 허용 가격은 0원 이상 1,000,000원 이하입니다."));
        }

        if (item.getStockQuantity() == null || item.getStockQuantity() < 5 || item.getStockQuantity() >= 1000) {
            bindingResult.addError(new FieldError("item", "stockQuantity", "재고 허용 갯수는 5개 이상 999개 이하입니다."));
        }


        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
        }
    }
}

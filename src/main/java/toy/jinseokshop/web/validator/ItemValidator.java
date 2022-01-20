package toy.jinseokshop.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import toy.jinseokshop.web.item.ItemFormDto;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.login.SessionLoginDto;
import toy.jinseokshop.web.member.MemberConst;

import javax.servlet.http.HttpServletRequest;

@Component
public class ItemValidator {

    public void itemAddFormValidate(ItemFormDto itemFormDto, BindingResult bindingResult) {
        // 1. 상품명 최소 1글자, 최대 29글자
        // 2. 상품가격 0 이상의 정수
        // 3. 상품갯수 0 이상의 정수

        // 1.
        if (!StringUtils.hasText(itemFormDto.getItemName()) || itemFormDto.getItemName().length() >= 30) {
            bindingResult.addError(new ObjectError("itemNameError", "상품명은 비어있거나 30글자를 초과할 수 없습니다."));
        }

        // 2. (상품가격은 정수이므로 정수가 아닌 수는 스프링이 자동으로 바인딩해준다(바인딩 결과를 어떻게 쓰는지 모르는게 문제지만..) 따라서 음의정수, 0만 검증해주면 됨!)
        if (itemFormDto.getPrice() <= 0) {
            bindingResult.addError(new ObjectError("priceError", "상품 가격은 1 이상의 정수여야 합니다."));
        }

        // 3. 2번과 같음
        if (itemFormDto.getStockQuantity() <= 0) {
            bindingResult.addError(new ObjectError("quantityError", "상품 수량은 1 이상의 정수여야 합니다."));
        }
    }

    public void isSellerValidate(HttpServletRequest request, BindingResult bindingResult) {
        SessionLoginDto sessionLoginDto = (SessionLoginDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        if (!sessionLoginDto.getIsSeller().equals(MemberConst.IS_SELLER)) {
            bindingResult.addError(new ObjectError("NoSeller", "판매자가 아닌 회원은 상품을 등록할 수 없습니다."));
        }
    }

}

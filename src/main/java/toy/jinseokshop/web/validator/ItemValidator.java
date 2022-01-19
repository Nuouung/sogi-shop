package toy.jinseokshop.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.login.SessionLoginDto;
import toy.jinseokshop.web.member.MemberConst;

import javax.servlet.http.HttpServletRequest;

@Component
public class ItemValidator {

    public void IsSellerValidate(HttpServletRequest request, BindingResult bindingResult) {
        SessionLoginDto sessionLoginDto = (SessionLoginDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        if (!sessionLoginDto.getIsSeller().equals(MemberConst.IS_SELLER)) {
            bindingResult.addError(new ObjectError("NoSeller", "판매자가 아닌 회원은 상품을 등록할 수 없습니다."));
        }
    }

}

package toy.jinseokshop.web.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.jinseokshop.domain.order.OrderService;
import toy.jinseokshop.web.validator.OrderValidator;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderValidator orderValidator;

    @PostMapping("/item/order")
    public String order(@ModelAttribute ItemDetailOrderDto orderDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 책의 수량이 음수이거나, 0이거나, 정수가 아닌 유리수일 때 bindingResult에 에러가 담긴다.
        orderValidator.BookOrderValidate(orderDto.getOrderItemQuantity(), bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("quantityError", "수량은 1 이상의 정수만 입력 가능합니다.");
            return "redirect:/item/detail/" + orderDto.getItemId();
        }

        // 위의 로직에서 메소드가 종료되지 않았다는 것은 상품이 잘 들어왔다는 뜻. 즉, bindingResult에는 에러가 없는 상태인 것이다.
        // 따라서 아래의 bindingResult.hasErrors() 에는 아무런 영향을 미치지 x
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(orderDto.getItemId());
        List<Integer> orderItemQuantities = new ArrayList<>();
        orderItemQuantities.add(orderDto.getOrderItemQuantity());
        Long orderId = orderService.save(orderDto.getEmail(), itemIds, orderItemQuantities, bindingResult);

        // 금액이 모자라 결제를 실패하면 orderService에서 결제가 진행되지 않고 튕겨 나온다.
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("moneyError", "금액이 모자랍니다. 내 정보에서 열정 금액을 충전해주세요.");
            return "redirect:/item/detail/" + orderDto.getItemId();
        }

        return "redirect:/myInfo";
    }

}

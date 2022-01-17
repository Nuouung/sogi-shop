package toy.jinseokshop.web.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.jinseokshop.domain.order.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/item/order")
    public String order(@ModelAttribute ItemDetailOrderDto orderDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
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

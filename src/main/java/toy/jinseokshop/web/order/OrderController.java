package toy.jinseokshop.web.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toy.jinseokshop.domain.order.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/item/order")
    public String order(@ModelAttribute ItemDetailOrderDto orderDto, BindingResult bindingResult) {
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(orderDto.getItemId());
        List<Integer> orderItemQuantities = new ArrayList<>();
        orderItemQuantities.add(orderDto.getOrderItemQuantity());
        Long orderId = orderService.save(orderDto.getEmail(), itemIds, orderItemQuantities, bindingResult);

        // 금액이 모자라 결제를 실패하면
        if (bindingResult.hasErrors()) {
            bindingResult.addError(new ObjectError("order", "금액이 부족합니다. 열정 금액을 더 충전해주세요."));
            return "redirect:/item/detail/" + orderDto.getItemId();
        }

        return "redirect:/order/success/" + orderId;
    }

}

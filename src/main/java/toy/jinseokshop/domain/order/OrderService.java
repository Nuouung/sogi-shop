package toy.jinseokshop.domain.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemRepository;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderItemRepository orderItemRepository;

    public Long save(String email, List<Long> itemIds, List<Integer> orderItemQuantities, BindingResult bindingResult) {
        // 회원을 가져온다 (구매 회원)
        Member member = memberRepository.findByEmail(email).orElse(null);
        
        // 상품을 가져온다 (회원이 구매한 상품)
        List<Item> items = storeItems(itemIds);

        // 주문상품, 배송정보를 만든다
        List<OrderItem> orderItems = createOrderItem(items, orderItemQuantities);
        Delivery delivery = createDelivery(member);


        // 만약 회원의 열정이 상품들의 열정가격보다 낮다면 화면으로 메세지를 출력해야 한다.
        int totalPrice = calculateTotalItemPrice(orderItems);
        assert member != null;
        if (member.getPassion() < totalPrice) {
            bindingResult.addError(new ObjectError("moneyError", "금액이 부족합니다. 열정 금액을 더 충전해주세요."));
            return null;
        }
        
        // 주문을 진행한다
        Order order = Order.createOrder(member, delivery, orderItems, totalPrice);
        return orderRepository.save(order);
    }

    public List<OrderItem> findOrderItems(Long memberId) {
        List<Order> orders = orderRepository.findAllByMemberId(memberId);

        List<OrderItem> result = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
            for (OrderItem orderItem : orderItems) {
                result.add(orderItem);
            }
        }
        return result;
    }

    private Delivery createDelivery(Member member) {
        Delivery delivery = Delivery.createDelivery(member);
        deliveryRepository.save(delivery);
        return delivery;
    }

    private int calculateTotalItemPrice(List<OrderItem> orderItems) {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderPrice();
        }
        return totalPrice;
    }

    private List<OrderItem> createOrderItem(List<Item> items, List<Integer> orderItemQuantities) {
        List<OrderItem> orderItems = new ArrayList<>();
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                log.info("===================================");
                log.info("item name : {}        item quantity : {}", items.get(i).getItemName(), orderItemQuantities.get(i));
                log.info("===================================");
                OrderItem orderItem = OrderItem.createOrderItem(items.get(i), orderItemQuantities.get(i));
                orderItemRepository.save(orderItem);
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    private List<Item> storeItems(List<Long> itemIds) {
        List<Item> items = new ArrayList<>();
        if (!itemIds.isEmpty()) {
            for (Long itemId : itemIds) {
                items.add(itemRepository.findById(itemId).orElse(null));
            }
        }
        return items;
    }

}

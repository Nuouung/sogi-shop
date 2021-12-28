package toy.jinseokshop.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemRepository;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 생성
     */
    public void order(String userId, Long itemId, int count) {
        // 멤버 조회
        Member member = memberRepository.findByUserId(userId).orElse(null);

        // 아이템 조회
        Item item = itemRepository.findByItemId(itemId).orElse(null);
        if (member == null || item == null) {
            throw new IllegalStateException("뭔가 정보가 없슴!!");
        }

        // 오더아이템 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count);

        // 딜리버리 생성
        Delivery delivery = Delivery.createDelivery();

        // 오더 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 오더 저장 -> delivery, orderItem 모두 저장됨. member, item은 건들 필요 없음
        orderRepository.save(order);
    }
}

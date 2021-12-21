package toy.jinseokshop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 저장 (포인트 - controller에서 조회한 세션 정보를 참고하여 현재 로그인한 회원정보를 Item 객체에 반영해야 한다.)
     */
    public Long saveOneItem(Item item, String loginId) {
        Member member = memberRepository.findByUserId(loginId).orElse(null);
        if (member == null) {
            return null;
        }
        item.setMember(member);
        return itemRepository.save(item);
    }

    /**
     * 로그인된 사용자가 판매자인지 검출하는 기능
     */
    public boolean isSeller(String loginId) {
        Member member = memberRepository.findByUserId(loginId).orElse(null);
        if (member == null) {
            return false;
        }
        return member.getSellerCheck().equals("y");
    }

    /**
     * 페이징 기능
     */
    public List<Item> getPage(int index) {
        int maxResult = ItemConst.NUMBER_PER_PAGE;
        int startPosition = (index - 1) * maxResult;

        return itemRepository.findPage(startPosition, maxResult);
    }
}

package toy.jinseokshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.jinseokshop.domain.item.ItemService;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.web.item.ItemDto;
import toy.jinseokshop.web.member.MemberDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final MemberRepository memberRepository;
    private final ItemService itemService;


    @PostConstruct
    public void initMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("jinseok");
        memberDto.setPassword("wlstjr57");
        memberDto.setAddressName("덕계동");
        Member member = new Member(memberDto);
        memberRepository.save(member);
    }

    @PostConstruct
    public void initItems() {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemName("책");
        itemDto.setPrice(10000);
        itemDto.setDType("B");
        itemDto.setOptionA("AAA");
        itemDto.setOptionB("BBB");
        itemDto.setStockQuantity(100);
        for (int i = 0; i < 20; i++) {
            itemService.saveItem(itemDto, new ArrayList<>());
        }
    }
}

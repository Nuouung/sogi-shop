//package toy.jinseokshop;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import toy.jinseokshop.domain.item.ItemService;
//import toy.jinseokshop.domain.member.Member;
//import toy.jinseokshop.domain.member.MemberRepository;
//import toy.jinseokshop.web.item.ItemFormDto;
//import toy.jinseokshop.web.member.MemberDto;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//
//@Component
//@RequiredArgsConstructor
//public class InitTestData {
//
//    private final MemberRepository memberRepository;
//    private final ItemService itemService;
//
//
//    @PostConstruct
//    public void initMemberAndItems() {
//        MemberDto memberDto = new MemberDto();
//        memberDto.setEmail("jinseok");
//        memberDto.setPassword("wlstjr57");
//        memberDto.setAddressName("덕계동");
//        Member member = new Member(memberDto);
//        memberRepository.save(member);
//
//        ItemFormDto itemFormDto = new ItemFormDto();
//        itemFormDto.setItemName("책");
//        itemFormDto.setPrice(10000);
//        itemFormDto.setDType("B");
//        itemFormDto.setOptionA("AAA");
//        itemFormDto.setOptionB("BBB");
//        itemFormDto.setStockQuantity(100);
//        for (int i = 0; i < 20; i++) {
//            itemService.saveItem(member.getEmail(), itemFormDto, new ArrayList<>());
//        }
//    }
//}

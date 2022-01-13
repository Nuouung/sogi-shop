package toy.jinseokshop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.file.FileRepository;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.domain.paging.PagingManager;
import toy.jinseokshop.domain.review.ReviewGroupDto;
import toy.jinseokshop.domain.review.ReviewRepository;
import toy.jinseokshop.web.item.ItemFormDto;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final ReviewRepository reviewRepository;

    private final PagingManager<Item> pagingManager;
    private final ItemPagingResolver itemPagingResolver;

    // 요구사항
    // 1. 아이템을 등록할 수 있어야 한다.
    public Long saveItem(String email, ItemFormDto itemFormDto, List<File> files) {
        // xxx, 혹은 ,xxx를 xxx로 변환한다.
        setOptions(itemFormDto);

        // 회원 엔티티를 조회한다
        Member member = memberRepository.findByEmail(email).orElse(null);

        Item item = Item.createItem(member, itemFormDto, files);
        itemRepository.save(item);

        // 파일이 포함되어 있는 상태로 등록 폼이 넘어올 경우
        // TODO 파일 사진만 넘어오도록 검증 로직 작성하여야 한다
        if (!item.getFiles().isEmpty()) {
            for (File file : files) {
                fileRepository.save(file);
            }
        }

        return item.getId();
    }

    // 2. 개별 아이템을 가져올 수 있어야 한다.
    public ItemDto findById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        String dType = getDType(item);
        if (item == null) {
            return null;
        }
        ReviewGroupDto reviewGroupDto = reviewRepository.findOneUsingGroupBy(item.getId());
        if (reviewGroupDto != null) {
            // 만족도(starRating)이 소수점 6자리?까지 나올 수 있기 때문에, 소수점 2번째 자리를 반올림해주는 메소드를 두었다
            double starRate = getStarRate(reviewGroupDto);
            return new ItemDto(item.getMember().getEmail(), item.getId(), item.getItemName(), item.getPrice(), starRate, reviewGroupDto.getReviewNumbers(), dType, item.getFiles());
        }
        return new ItemDto(item.getMember().getEmail(), item.getId(), item.getItemName(),item.getPrice(), 0, 0L, dType, item.getFiles());
    }

    // 3. 페이징 한 아이템 리스트를 가져올 수 있어야 한다.
    public Map<String, Object> getPage(int queryParam) {
        int[] indexes = pagingManager.getPageIndexes(queryParam);
        Map<String, Object> itemMap = itemRepository.findPage(indexes[0], indexes[1]);

        // Item과 Review 객체를 합쳐 새로운 ItemListDto List를 만들어 Map에 담는 메소드
        // 이 과정에서 기존 Item List는 삭제된다. (ItemListDto List로 대체)
        return itemPagingResolver.createItemListDtos(itemMap, indexes[0], indexes[1]);
    }

    // 4. 아이템 수정을 할 수 있어야 한다. (주의점 : 파일 추가, 파일 삭제)
    // TODO 파일 사진만 넘어오도록 검증 로직 작성해야 한다!

    // 5. 아이템 삭제를 할 수 있어야 한다.

    private void setOptions(ItemFormDto itemFormDto) {
        for (String s : itemFormDto.getOptionA().split(",")) {
            if (s.length() != 0) {
                itemFormDto.setOptionA(s);
            }
        }

        for (String s : itemFormDto.getOptionB().split(",")) {
            if (s.length() != 0) {
                itemFormDto.setOptionB(s);
            }
        }
    }

    private String getDType(Item item) {
        String dType = null;
        if (item != null) {
            switch (item.getStockQuantity()) {
                case ItemConst.SYMBOL_OF_LECTURE:
                    dType = ItemConst.LECTURE;
                    break;
                case ItemConst.SYMBOL_OF_ETC:
                    dType = ItemConst.ETC;
                    break;
                default:
                    dType = ItemConst.BOOK;
            }
        }
        return dType;
    }

    private double getStarRate(ReviewGroupDto reviewGroupDto) {
        return Math.round(reviewGroupDto.getStarRating() * 10) / 10.0;
    }
}

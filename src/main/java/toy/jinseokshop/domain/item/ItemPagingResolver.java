package toy.jinseokshop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.jinseokshop.domain.paging.PageConst;
import toy.jinseokshop.domain.review.ReviewListDto;
import toy.jinseokshop.domain.review.ReviewRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ItemPagingResolver {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;

    public Map<String, Object> createItemListDtos(Map<String, Object> itemMap, int startPosition, int maxResult) {
        // 페이징된 아이템들의 아이디를 추출
        List<Long> itemIds = itemRepository.findPageIds(startPosition, maxResult);
        // 아이템들의 아이디를 기반으로 그에 해당되는 리뷰만을 추출
        Map<Long, ReviewListDto> reviewMap = reviewRepository.findPageWithGroupBy(itemIds);
        // 페이징된 아이템들을 추출
        List<?> itemList = new ArrayList<>((Collection<?>) itemMap.get(PageConst.LIST));

        // reviewMap과 itemList를 이용, 새로운 ItemListDto를 만들어야 한다.
        List<ItemListDto> itemListDtos = createItemListDtos(reviewMap, itemList);

        // itemMap에 들어 있는 원래 아이템 객체를 삭제하고, 새로운 아이템리스트디티오 객체를 넣어야 한다.
        restoreItemMap(itemMap, itemListDtos);

        // 끝. 새로운 itemMap 반환
        return itemMap;
    }

    private void restoreItemMap(Map<String, Object> itemMap, List<ItemListDto> itemListDtos) {
        itemMap.remove(PageConst.LIST);
        itemMap.put(PageConst.LIST, itemListDtos);
    }

    private List<ItemListDto> createItemListDtos(Map<Long, ReviewListDto> reviewMap, List<?> itemList) {
        List<ItemListDto> itemListDtos = new ArrayList<>();
        for (Object objectItem : itemList) {
            Item item = (Item) objectItem;
            ReviewListDto reviewListDto = reviewMap.get(item.getId());
            ItemListDto itemListDto = null;
            if (reviewListDto != null) {
                itemListDto = new ItemListDto(item.getId(), item.getItemName(), item.getPrice(), reviewListDto.getStarRating(), reviewListDto.getReviewNumbers());
            } else {
                itemListDto = new ItemListDto(item.getId(), item.getItemName(), item.getPrice(), 0, 0L);
            }
            itemListDtos.add(itemListDto);
        }
        return itemListDtos;
    }


}

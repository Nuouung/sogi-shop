package toy.jinseokshop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.file.FileRepository;
import toy.jinseokshop.domain.paging.PagingManager;
import toy.jinseokshop.web.item.ItemDto;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileRepository fileRepository;
    private final PagingManager<Item> pagingManager;

    // 요구사항
    // 1. 아이템을 등록할 수 있어야 한다.
    public Long saveItem(ItemDto itemDto, List<File> files) {
        // xxx, 혹은 ,xxx를 xxx로 변환한다.
        setOptions(itemDto);

        Item item = Item.createItem(itemDto, files);
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
    public Item findById(Long id) {
        Optional<Item> foundItem = itemRepository.findById(id);
        return foundItem.orElse(null);
    }

    // 3. 페이징 한 아이템 리스트를 가져올 수 있어야 한다.
    public Map<String, Object> getPage(int queryParam) {
        int[] indexes = pagingManager.getPageIndexes(queryParam);

        // TODO findPage에 실려 있는 Item 객체를 ItemListDto에 실어서 보내려 한다.
        // 리뷰가 완성되지 않았으나, 이 기능은 리뷰와 강한 연결을 가지고 있기 때문에 리뷰를 완성한 후 반드시 다시 볼 것

        return itemRepository.findPage(indexes[0], indexes[1]);
    }

    // 4. 아이템 수정을 할 수 있어야 한다. (주의점 : 파일 추가, 파일 삭제)
    // TODO 파일 사진만 넘어오도록 검증 로직 작성해야 한다!

    // 5. 아이템 삭제를 할 수 있어야 한다.

    private void setOptions(ItemDto itemDto) {
        for (String s : itemDto.getOptionA().split(",")) {
            if (s.length() != 0) {
                itemDto.setOptionA(s);
            }
        }

        for (String s : itemDto.getOptionB().split(",")) {
            if (s.length() != 0) {
                itemDto.setOptionB(s);
            }
        }
    }
}

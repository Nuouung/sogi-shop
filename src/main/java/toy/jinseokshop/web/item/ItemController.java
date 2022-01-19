package toy.jinseokshop.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemConst;
import toy.jinseokshop.domain.item.ItemDto;
import toy.jinseokshop.domain.item.ItemService;
import toy.jinseokshop.domain.paging.PagingManager;
import toy.jinseokshop.domain.review.ReviewDto;
import toy.jinseokshop.domain.review.ReviewService;
import toy.jinseokshop.web.file.FileStorageManager;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.login.SessionLoginDto;
import toy.jinseokshop.web.validator.ItemValidator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ReviewService reviewService;

    private final PagingManager<Item> pagingManager;
    private final FileStorageManager fileStorageManager;

    private final ItemValidator itemValidator;

    @GetMapping
    public String itemList(@RequestParam int page, @Nullable @RequestParam String noSeller, Model model, HttpServletRequest request) {
        // 쿼리파라미터로 받은 page를 바탕으로 Item 객체 리스트를 페이징 해서 뷰 단으로 띄워준다.
        // 이 메소드 하나면 컨트롤러 단에서의 페이징 끝.
        pagingManager.storePageToModel(itemService.getPage(page, "main"), page, model);

        // 판매자가 아닌 사람이 판매 버튼을 누르면 에러 메세지가 출력됨
        model.addAttribute("NoSeller", noSeller);
        return "items/itemList";
    }

    @GetMapping("/{type}")
    public String itemCategoryList(@RequestParam int page, @PathVariable String type, Model model, HttpServletRequest request) {
        // 쿼리파라미터로 받은 page를 바탕으로 Item 객체 리스트를 페이징 해서 뷰 단으로 띄워준다.
        // 이 메소드 하나면 컨트롤러 단에서의 페이징 끝.
        pagingManager.storePageToModel(itemService.getPage(page, type), page, model);

        return "items/itemList";
    }

    @GetMapping("/detail/{id}")
    public String itemDetail(@PathVariable Long id, @Nullable @RequestParam("moneyError") String err, Model model, HttpServletRequest request) {
        ItemDto itemDto = itemService.findById(id);
        List<ReviewDto> reviewDtoList = reviewService.findByItemId(id);

        if (itemDto == null) {
            return "redirect:/item";
        }

        String queryParam = getRefererQueryParameter(request);
        model.addAttribute("item", itemDto);
        model.addAttribute("queryParam", queryParam);
        model.addAttribute("reviews", reviewDtoList);
        model.addAttribute("moneyError", err);

        return "items/itemDetail";
    }

    @GetMapping("/add")
    public String itemAddForm(@ModelAttribute ItemFormDto itemFormDto, BindingResult bindingResult, HttpServletRequest request, Model model) {

        itemValidator.IsSellerValidate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/item?page=1&noSeller=true";
        }

        model.addAttribute("item", new ItemFormDto());
        return "items/itemAddForm";
    }

    @PostMapping("/add")
    public String itemAdd(@ModelAttribute(name = "item") ItemFormDto itemFormDto, HttpServletRequest request) throws IOException {
        List<File> files = fileStorageManager.store(itemFormDto.getFiles());

        // 자바스크립트에서 막기는 했지만 사용자가 자바스크립트를 조작해 optionA, B가 2개로 넘어올 수 있다.
        // optionA, B가 2개로 넘어오면 서비스 장애가 날 가능성이 있으니 사전조치하자.
        boolean maliciousApproach = maliciousClientApproachCatcher(itemFormDto.getOptionA(), itemFormDto.getOptionB());

        if (maliciousApproach) {
            return "redirect:/";
        }

        if (itemFormDto.getDType().equals(ItemConst.ETC)) {
            itemFormDto.setOptionA("X");
            itemFormDto.setOptionB("X");
        }
        SessionLoginDto sessionLoginDto = (SessionLoginDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        String email = sessionLoginDto.getEmail();
        Long id = itemService.saveItem(email, itemFormDto, files);
        return "redirect:/item/detail/" + id;
    }

    private String getRefererQueryParameter(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (referer != null) {
            String[] arrayForURL = referer.split("=");
            if (arrayForURL.length == 2) return arrayForURL[1];
        }
        return "1";
    }

    private boolean maliciousClientApproachCatcher(String optionA, String optionB) {
        int indexA = optionA.lastIndexOf(",");
        int indexB = optionB.lastIndexOf(",");
        int lastIndexA = optionA.length() - 1;
        int lastIndexB = optionB.length() - 1;

        // 가능한 String 형태
        // 1. [정상 접근] xxxx,
        // 2. [정상 접근] ,xxxx
        // 3. [비정상 접근] xxxx,xxxx
        if ((indexA != 0 && indexA != lastIndexA) ||
                (indexB != 0 && indexB != lastIndexB)) {
            log.warn("MALICIOUS APPROACH DETECTED");
            return true;
        }
        return false;
    }

}

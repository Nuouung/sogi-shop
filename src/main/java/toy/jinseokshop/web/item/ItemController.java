package toy.jinseokshop.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemService;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.validator.ItemValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemValidator itemValidator;

    @GetMapping
    public String toItems(@RequestParam int page, Model model, HttpServletRequest request) {
        String loginId = (String) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        boolean isSeller = itemService.isSeller(loginId);

        List<Item> itemList = itemService.getPage(page);
        model.addAttribute("itemList", itemList);
        model.addAttribute("isSeller", isSeller);

        return "/items/itemList";
    }

    @GetMapping("/add")
    public String addItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "/items/itemAddForm";
    }

    // TODO 검증 기능이 완벽하지 않다. 공부 하고 보완하도록 하자.
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, HttpServletRequest request, BindingResult bindingResult, Model model) {

        itemValidator.writeValidate(item, bindingResult);
        String loginId = (String) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

        if (bindingResult.hasErrors()) {
            return "/items/itemAddForm";
        }

        Long result = itemService.saveOneItem(item, loginId);
        if (result == null) {
            return "/items/itemAddForm";
        }

        return "redirect:/items?page=1";
    }

    @GetMapping("/item/{itemId}")
    public String itemDetail(@PathVariable Long itemId) {
        return "/items/itemDetail";
    }

    @GetMapping("/item/{itemId}/update")
    public String itemUpdateForm(@PathVariable Long itemId) {
        return "/items/itemUpdateForm";
    }

    @PostMapping("/item/{itemId}/update")
    public String itemUpdate(@PathVariable Long itemId) {
        return "redirect:/items/item/" + itemId;
    }

    @PostMapping("/item/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        return "redirect:/items?page=1";
    }

}

package toy.jinseokshop.domain.item;

import lombok.Getter;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.member.Member;

import java.util.List;

@Getter
public class ItemDto {

    private String email;
    private Long id;
    private String itemName;
    private String content;
    private int price;
    private double starRating;
    private Long reviewNumbers;

    private String category;

    private Member member;
    private List<File> files;

    public ItemDto() {
    }

    public ItemDto(Long id, String itemName, int price, double starRating, Long reviewNumbers, List<File> files) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.starRating = starRating;
        this.reviewNumbers = reviewNumbers;
        this.files = files;
    }

    public ItemDto(String email, Long id, String itemName, String content, int price, double starRating, Long reviewNumbers, String category, List<File> files) {
        this.email = email;
        this.id = id;
        this.itemName = itemName;
        this.content = content;
        this.price = price;
        this.starRating = starRating;
        this.reviewNumbers = reviewNumbers;
        switch (category) {
            case ItemConst.BOOK:
                this.category = "책";
                break;
            case ItemConst.LECTURE:
                this.category = "강의";
                break;
            case ItemConst.ETC:
                this.category = "기타";
        }
        this.files = files;
    }
}

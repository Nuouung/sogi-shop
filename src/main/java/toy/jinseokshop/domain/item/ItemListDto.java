package toy.jinseokshop.domain.item;

import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.file.File;

import java.util.List;

@Getter
public class ItemListDto {

    private Long id;
    private String itemName;
    private int price;
    private double starRating;
    private Long reviewNumbers;

    private List<File> files;

    public ItemListDto() {
    }

    public ItemListDto(Long id, String itemName, int price, double starRating, Long reviewNumbers, List<File> files) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.starRating = starRating;
        this.reviewNumbers = reviewNumbers;
        this.files = files;
    }
}

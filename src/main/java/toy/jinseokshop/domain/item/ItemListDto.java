package toy.jinseokshop.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ItemListDto {

    private Long id;
    private String itemName;
    private int price;
    private double starRating;
    private Long reviewNumbers;

    public ItemListDto() {
    }

    public ItemListDto(Long id, String itemName, int price, double starRating, Long reviewNumbers) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.starRating = starRating;
        this.reviewNumbers = reviewNumbers;
    }
}

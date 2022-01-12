package toy.jinseokshop.domain.review;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewListDto {

    private Long itemId;
    private double starRating;
    private Long reviewNumbers;

    public ReviewListDto() {
    }

    public ReviewListDto(Long itemId, double starRating, Long reviewNumbers) {
        this.itemId = itemId;
        this.starRating = starRating;
        this.reviewNumbers = reviewNumbers;
    }
}

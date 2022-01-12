package toy.jinseokshop.domain.review;

import lombok.Getter;

@Getter
public class ReviewDto {

    private String email;
    private String content;
    private int stars;

    public ReviewDto() {
    }

    public ReviewDto(String email, String content, int stars) {
        this.email = email;
        this.content = content;
        this.stars = stars;
    }
}

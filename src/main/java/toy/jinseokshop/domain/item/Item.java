package toy.jinseokshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.review.Review;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long id;

    private String itemName;

    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<File> files = new ArrayList<>();

    private String dtype;

    //==> 연관관계 메소드
    public void addReview(Review review) {
        reviews.add(review);
        review.setItem(this);
    }

    public void addFile(File file) {
        files.add(file);
        file.setItem(this);
    }
}

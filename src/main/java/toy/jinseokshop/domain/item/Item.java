package toy.jinseokshop.domain.item;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.review.Review;
import toy.jinseokshop.web.file.FileDto;
import toy.jinseokshop.web.item.ItemDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "D_TYPE")
@Getter @Setter(AccessLevel.PROTECTED)
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

    //==> 연관관계 메소드
    private void addReview(Review review) {
        reviews.add(review);
        review.setItem(this);
    }

    private void addFile(File file) {
        files.add(file);
        file.setItem(this);
    }

    //==> 정적 팩토리 메소드
    public static Item createItem(ItemDto itemDto, List<File> files) {
        Item item = storeItemDto(itemDto);
        if (!files.isEmpty()) {
            storeFileList(files, item);
        }
        return item;
    }

    public static Item updateFile(Item item, File file) {
        item.addFile(file);
        return item;
    }

    private static void storeFileList(List<File> files, Item item) {
        for (File file : files) {
            assert item != null;
            item.addFile(file);
        }
    }

    private static Item storeItemDto(ItemDto itemDto) {
        Item item = new Item();
        switch (itemDto.getDType()) {
            case "B":
                item = Book.createBook(itemDto.getItemName(), itemDto.getPrice(), itemDto.getStockQuantity(), itemDto.getOptionA(), itemDto.getOptionB());
                break;
            case "L":
                item = Lecture.createLecture(itemDto.getItemName(), itemDto.getPrice(), itemDto.getOptionA(), itemDto.getOptionB());
                break;
        }
        return item;
    }


}

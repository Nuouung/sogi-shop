package toy.jinseokshop.domain.item;

import lombok.*;
import toy.jinseokshop.domain.file.File;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.review.Review;
import toy.jinseokshop.web.item.ItemFormDto;

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
    private String content;

    private int price;
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

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

    private void setMember(Member member) {
        this.member = member;
        member.addItems(this);
    }

    //==> 정적 팩토리 메소드
    public static Item createItem(Member member, ItemFormDto itemFormDto, List<File> files) {
        Item item = storeItemFormDto(itemFormDto);
        item.setMember(member);
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

    private static Item storeItemFormDto(ItemFormDto itemFormDto) {
        Item item = new Item();
        switch (itemFormDto.getDType()) {
            case ItemConst.BOOK:
                item = Book.createBook(itemFormDto.getItemName(), itemFormDto.getContent(), itemFormDto.getPrice(), itemFormDto.getStockQuantity(), itemFormDto.getOptionA(), itemFormDto.getOptionB());
                break;
            case ItemConst.LECTURE:
                item = Lecture.createLecture(itemFormDto.getItemName(), itemFormDto.getContent(), itemFormDto.getPrice(), itemFormDto.getOptionA(), itemFormDto.getOptionB());
                break;
            case ItemConst.ETC:
                item = Etcetera.createEtc(itemFormDto.getItemName(), itemFormDto.getContent(), itemFormDto.getPrice());
        }
        return item;
    }


}

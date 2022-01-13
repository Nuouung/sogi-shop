package toy.jinseokshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("L")
@Getter @Setter(AccessLevel.PRIVATE)
public class Lecture extends Item {

    private String instructor;
    private String platform;

    //==> 정적 팩토리 메소드
    public static Lecture createLecture(String itemName, String content, int price, String instructor, String platform) {
        Lecture lecture = new Lecture();
        lecture.setItemName(itemName);
        lecture.setContent(content);
        lecture.setPrice(price);
        lecture.setStockQuantity(ItemConst.SYMBOL_OF_LECTURE);
        lecture.setInstructor(instructor);
        lecture.setPlatform(platform);
        return lecture;
    }

}

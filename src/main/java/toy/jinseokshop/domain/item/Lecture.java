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
    public static Lecture createLecture(String itemName, int price, String instructor, String platform) {
        Lecture lecture = new Lecture();
        lecture.setItemName(itemName);
        lecture.setPrice(price);
        lecture.setStockQuantity(-1);
        lecture.setInstructor(instructor);
        lecture.setPlatform(platform);
        return lecture;
    }

}

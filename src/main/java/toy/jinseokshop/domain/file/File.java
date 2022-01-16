package toy.jinseokshop.domain.file;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.jinseokshop.domain.Base;
import toy.jinseokshop.domain.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class File extends Base {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private String originalFileName;
    private String convertedFileName;

    //==> 연관관계 메소드
    public void setItem(Item item) {
        this.item = item;
    }

    public File(String originalFileName, String convertedFileName) {
        this.originalFileName = originalFileName;
        this.convertedFileName = convertedFileName;
    }
}

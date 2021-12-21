package toy.jinseokshop.domain.item;

import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long itemId;

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID") // 이후 Item의 member column은 fk로 Member의 member_id column을 매핑한다
    private Member member; // 단방향 설정 <- 이후 필요시 양방향 고려할 것

    private Integer price;
    private Integer stockQuantity;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}

package toy.jinseokshop.domain.item;

import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.exception.NotEnoughStockException;
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

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "MEMBER_ID") // 이후 Item의 member column은 fk로 Member의 member_id column을 매핑한다
    private Member member; // 단방향 설정 <- 이후 필요시 양방향 고려할 것

    private Integer price;
    private Integer stockQuantity;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // == 비지니스 로직 == //
    /**
     * 상품 재고 감소 로직
     */
    public void reduceStockQuantity(int count) {
        int restStock = this.stockQuantity - count;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고 수량이 부족하나이다");
        }

        this.stockQuantity = restStock;
    }

}

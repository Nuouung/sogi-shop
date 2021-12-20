package toy.jinseokshop.domain.board;

import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    private String title;
    private String contents;
    private String writer;

    private Integer readCount;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}

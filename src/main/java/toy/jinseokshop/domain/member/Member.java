package toy.jinseokshop.domain.member;

import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.board.Board;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String userId;
    private String password;
    private String sellerCheck;
    private LocalDateTime createdDate;

//    @OneToMany(mappedBy = "member")
//    private List<Board> boards = new ArrayList<>();
//
//    // == 연관관계 메소드 == //
//    public void addBoards(Board board) {
//        this.boards.add(board);
//        board.setMember(this);
//    }

    // == 비지니스 로직 == //
    public Member createMember(Member member) {
        Member newMember = new Member();

        newMember.userId = member.getUserId();
        newMember.password = member.getPassword();
        newMember.sellerCheck = (member.getSellerCheck() == null) ? "n" : "y";
        newMember.createdDate = LocalDateTime.now();

        return newMember;
    }

}

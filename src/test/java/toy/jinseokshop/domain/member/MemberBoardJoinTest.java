package toy.jinseokshop.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class MemberBoardJoinTest {

    @Autowired EntityManager em;

    @Test
    public void 값을한번넣어보자() {
        Member member = new Member();
        member.setId(1L);
        member.setUserId("진석쿠");
        member.setPassword("123456");


    }

}
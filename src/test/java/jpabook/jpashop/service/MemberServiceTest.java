package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService service;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원가입")
    void test1() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = service.join(member);

        // then
        em.flush();
        assertEquals(member, repository.findOne(savedId));
    }

    @Test
    @DisplayName("중복회원예외")
    void test2() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when

        // then
        assertThrows(IllegalStateException.class, () -> {
            service.join(member1);
            service.join(member2);
        });
    }

}
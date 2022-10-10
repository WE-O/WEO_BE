package bside.weo.plant.repository;

import bside.weo.plant.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    //private final JPAQueryFactory queryFactory;

    /*
    public MemberRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    */

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //querydsl로 변경 필요!
    public List<Member> findBySnsId(String snsId) {
        return em.createQuery("select u from Member u where u.snsId = :snsId", Member.class)
                .setParameter("snsId", snsId)
                .getResultList();
    }
}

package com.plant.web.api.member.adapter.out.persistence;

import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface MemberJpaRepository extends JpaRepository<Member, Serializable> {

    Member getByMemberId(String memberId);
}

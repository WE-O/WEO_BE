package com.plant.web.api.member.adapter.out.persistence;

import com.plant.web.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface MemberJpaRepository extends JpaRepository<Member, Serializable> {
}

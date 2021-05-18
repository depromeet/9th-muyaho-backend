package com.depromeet.muyaho.domain.domain.member;

import com.depromeet.muyaho.domain.domain.member.repository.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}

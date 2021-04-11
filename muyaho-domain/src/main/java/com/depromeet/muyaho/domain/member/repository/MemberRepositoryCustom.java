package com.depromeet.muyaho.domain.member.repository;

import com.depromeet.muyaho.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findMemberByEmail(String email);

}

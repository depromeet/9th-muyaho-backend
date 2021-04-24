package com.depromeet.muyaho.domain.member.repository;

import com.depromeet.muyaho.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findMemberById(Long memberId);

    Member findMemberByUid(String uid);

}

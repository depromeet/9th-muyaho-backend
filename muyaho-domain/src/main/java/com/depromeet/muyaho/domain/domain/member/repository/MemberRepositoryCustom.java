package com.depromeet.muyaho.domain.domain.member.repository;

import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;

public interface MemberRepositoryCustom {

    Member findMemberById(Long memberId);

    Member findMemberByUidAndProvider(String uid, MemberProvider provider);

    Member findMemberByName(String name);

}

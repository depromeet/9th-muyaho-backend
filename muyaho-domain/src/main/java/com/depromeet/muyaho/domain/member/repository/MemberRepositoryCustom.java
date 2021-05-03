package com.depromeet.muyaho.domain.member.repository;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;

public interface MemberRepositoryCustom {

    Member findMemberById(Long memberId);

    Member findMemberByUidAndProvider(String uid, MemberProvider provider);

    Member findMemberByName(String name);

}

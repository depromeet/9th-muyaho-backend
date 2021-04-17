package com.depromeet.muyaho.domain.member.repository;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;

public interface MemberRepositoryCustom {

    Member findMemberByEmailAndProvider(String email, MemberProvider provider);

}

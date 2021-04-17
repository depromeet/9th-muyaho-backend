package com.depromeet.muyaho.domain.member.repository;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.muyaho.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findMemberByEmailAndProvider(String email, MemberProvider provider) {
        return queryFactory.selectFrom(member)
            .where(
                member.email.email.eq(email),
                member.provider.eq(provider)
            ).fetchOne();
    }

}

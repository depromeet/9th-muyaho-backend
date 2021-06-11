package com.depromeet.muyaho.domain.domain.member.repository;

import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.muyaho.domain.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findMemberById(Long memberId) {
        return queryFactory.selectFrom(member)
            .where(
                member.id.eq(memberId)
            ).fetchOne();
    }

    @Override
    public Member findMemberByUidAndProvider(String uid, MemberProvider provider) {
        return queryFactory.selectFrom(member)
            .where(
                member.uid.eq(uid),
                member.provider.eq(provider)
            ).fetchOne();
    }

    @Override
    public Member findMemberByName(String name) {
        return queryFactory.selectFrom(member)
            .where(
                member.name.eq(name)
            ).fetchOne();
    }

}

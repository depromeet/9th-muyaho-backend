package com.depromeet.muyaho.domain.member.repository;

import com.depromeet.muyaho.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.muyaho.domain.member.QMember.member;

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
    public Member findMemberByUid(String uid) {
        return queryFactory.selectFrom(member)
            .where(
                member.uid.eq(uid)
            ).fetchOne();
    }

}

package com.depromeet.muyaho.domain.memberstock.repository;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.muyaho.domain.memberstock.QMemberStock.memberStock;

@RequiredArgsConstructor
public class MemberStockRepositoryCustomImpl implements MemberStockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberStock findStockByMemberIdAndStockId(Long memberId, Long stockId) {
        return queryFactory.selectFrom(memberStock)
            .where(
                memberStock.memberId.eq(memberId),
                memberStock.stockId.eq(stockId)
            ).fetchOne();
    }

}

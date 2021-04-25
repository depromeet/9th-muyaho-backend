package com.depromeet.muyaho.domain.memberstock.repository;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.muyaho.domain.memberstock.QMemberStock.memberStock;
import static com.depromeet.muyaho.domain.stock.QStock.stock;

@RequiredArgsConstructor
public class MemberStockRepositoryCustomImpl implements MemberStockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberStock findStockByMemberIdAndStockId(Long memberId, Long stockId) {
        return queryFactory.selectFrom(memberStock)
            .innerJoin(memberStock.stock, stock)
            .where(
                memberStock.memberId.eq(memberId),
                stock.id.eq(stockId)
            ).fetchOne();
    }

    @Override
    public List<MemberStock> findAllStocksByMemberId(Long memberId) {
        return queryFactory.selectFrom(memberStock)
            .innerJoin(memberStock.stock, stock).fetchJoin()
            .where(
                memberStock.memberId.eq(memberId)
            ).fetch();
    }

}

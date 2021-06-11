package com.depromeet.muyaho.domain.domain.dailystockamount.repository;

import com.depromeet.muyaho.domain.domain.dailystockamount.DailyStockAmount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.muyaho.domain.domain.dailystockamount.QDailyStockAmount.dailyStockAmount;

@RequiredArgsConstructor
public class DailyStockAmountRepositoryCustomImpl implements DailyStockAmountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DailyStockAmount findLastDailyStockAmount(Long memberId) {
        return queryFactory.selectFrom(dailyStockAmount)
            .where(
                dailyStockAmount.memberId.eq(memberId)
            )
            .orderBy(
                dailyStockAmount.localDateTime.desc()
            )
            .fetchFirst();
    }

}

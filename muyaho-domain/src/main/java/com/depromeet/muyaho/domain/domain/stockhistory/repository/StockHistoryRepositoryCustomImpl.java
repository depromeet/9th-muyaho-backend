package com.depromeet.muyaho.domain.domain.stockhistory.repository;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.muyaho.domain.domain.stockhistory.QStockHistory.stockHistory;
import static com.depromeet.muyaho.domain.domain.memberstock.QMemberStock.memberStock;

@RequiredArgsConstructor
public class StockHistoryRepositoryCustomImpl implements StockHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StockHistory> findAllByMemberIdAndType(Long memberId, StockMarketType type) {
        return queryFactory.selectFrom(stockHistory)
            .innerJoin(stockHistory.memberStock, memberStock).fetchJoin()
            .where(
                stockHistory.memberStock.memberId.eq(memberId),
                stockHistory.memberStock.stock.type.eq(type)
            ).fetch();
    }

}

package com.depromeet.muyaho.domain.stock.repository;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.muyaho.domain.stock.QStock.stock;

@RequiredArgsConstructor
public class StockRepositoryCustomImpl implements StockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Stock> findStocksByMemberId(Long memberId) {
        return queryFactory.selectFrom(stock)
            .where(
                stock.memberId.eq(memberId)
            ).fetch();
    }

    @Override
    public Stock findStockByCodeAndTypeAndMemberId(Long memberId, StockType type, String stockCode) {
        return queryFactory.selectFrom(stock)
            .where(
                stock.memberId.eq(memberId),
                stock.type.eq(type),
                stock.stockCode.eq(stockCode)
            ).fetchOne();
    }

}

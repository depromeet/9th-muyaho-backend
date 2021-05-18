package com.depromeet.muyaho.domain.domain.stock.repository;

import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.muyaho.domain.domain.stock.QStock.stock;

@RequiredArgsConstructor
public class StockRepositoryCustomImpl implements StockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Stock> findAllByType(StockMarketType type) {
        return queryFactory.selectFrom(stock)
            .where(
                stock.type.eq(type)
            ).fetch();
    }

    @Override
    public List<Stock> findAllActiveStockByType(StockMarketType type) {
        return queryFactory.selectFrom(stock)
            .where(
                stock.type.eq(type),
                stock.status.eq(StockStatus.ACTIVE)
            )
            .orderBy(stock.name.asc())
            .fetch();
    }

    @Override
    public Stock findActiveStockById(Long stockId) {
        return queryFactory.selectFrom(stock)
            .where(
                stock.id.eq(stockId),
                stock.status.eq(StockStatus.ACTIVE)
            ).fetchOne();
    }

}

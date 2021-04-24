package com.depromeet.muyaho.domain.stock.repository;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.stock.StockStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.depromeet.muyaho.domain.stock.QStock.stock;

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
            .orderBy(stock.id.asc())
            .fetch();
    }

}

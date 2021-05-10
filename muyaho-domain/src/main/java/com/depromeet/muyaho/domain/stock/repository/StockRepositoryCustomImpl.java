package com.depromeet.muyaho.domain.stock.repository;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.stock.StockStatus;
import com.querydsl.core.BooleanBuilder;
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
                stock.status.eq(StockStatus.ACTIVE),
                filterCurrency(type.getAllowedCurrencies())
            )
            .orderBy(stock.id.asc())
            .fetch();
    }

    private BooleanBuilder filterCurrency(List<String> allowedCurrencies) {
        BooleanBuilder builder = new BooleanBuilder();
        for (String allowedCurrency : allowedCurrencies) {
            builder.or(stock.code.startsWith(allowedCurrency));
        }
        return builder;
    }

    @Override
    public Stock findStockById(Long stockId) {
        return queryFactory.selectFrom(stock)
            .where(
                stock.id.eq(stockId),
                stock.status.eq(StockStatus.ACTIVE)
            ).fetchOne();
    }

}

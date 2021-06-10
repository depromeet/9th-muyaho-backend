package com.depromeet.muyaho.domain.service.stock.dto.request;

import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockInfoRequest {

    private String code;

    private String name;

    public static StockInfoRequest of(String code, String name) {
        return new StockInfoRequest(code, name);
    }

    public Stock toEntity(StockMarketType type) {
        return Stock.newInstance(type, code, name);
    }

    public boolean isAllowed(StockMarketType type) {
        return type.isAllowedCode(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockInfoRequest that = (StockInfoRequest) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}

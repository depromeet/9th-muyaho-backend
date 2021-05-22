package com.depromeet.muyaho.domain.domain.stock;

import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.common.exception.ForbiddenException;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = @Index(name = "idx_stock_1", columnList = "type,code"))
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StockMarketType type;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    @Builder
    Stock(StockMarketType type, String code, String name, StockStatus status) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public static Stock newInstance(StockMarketType type, String code, String name) {
        return Stock.builder()
            .type(type)
            .code(code)
            .name(name)
            .status(StockStatus.ACTIVE)
            .build();
    }

    public Stock disable() {
        this.status = StockStatus.DISABLED;
        return this;
    }

    public Stock active() {
        this.status = StockStatus.ACTIVE;
        return this;
    }

    public void validateAllowCurrency(CurrencyType currencyType) {
        if (!type.isAllowCurrencyType(currencyType)) {
            throw new ForbiddenException(String.format("주식 (%s)은 통화 (%s)를 허용하지 않습니다", type, currencyType), ErrorCode.FORBIDDEN_STOCK_CURRENCY_EXCEPTION);
        }
    }

}

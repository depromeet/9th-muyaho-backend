package com.depromeet.muyaho.domain.domain.stockchange;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StockPriceChange extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long stockId;

    private BigDecimal price;

    private StockPriceChange(Long stockId, BigDecimal price) {
        this.stockId = stockId;
        this.price = price;
    }

    public static StockPriceChange of(Long stockId, BigDecimal price) {
        return new StockPriceChange(stockId, price);
    }

}

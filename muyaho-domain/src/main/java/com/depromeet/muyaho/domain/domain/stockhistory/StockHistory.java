package com.depromeet.muyaho.domain.domain.stockhistory;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StockHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_stock_id", nullable = false)
    private MemberStock memberStock;

    private BigDecimal currentPriceInWon;

    private BigDecimal currentPriceInDollar;

    private BigDecimal profitOrLoseRate;

    @Builder
    public StockHistory(MemberStock memberStock, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        this.memberStock = memberStock;
        this.currentPriceInWon = currentPriceInWon;
        this.currentPriceInDollar = currentPriceInDollar;
        this.profitOrLoseRate = profitOrLoseRate;
    }

}

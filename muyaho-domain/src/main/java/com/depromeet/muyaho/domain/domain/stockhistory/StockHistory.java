package com.depromeet.muyaho.domain.domain.stockhistory;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Column(nullable = false)
    private BigDecimal currentPriceInWon;

    @Column(nullable = false)
    private BigDecimal currentPriceInDollar;

    @Column(nullable = false)
    private BigDecimal profitOrLoseRate;

    @Builder
    public StockHistory(MemberStock memberStock, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        this.memberStock = memberStock;
        this.currentPriceInWon = currentPriceInWon;
        this.currentPriceInDollar = currentPriceInDollar;
        this.profitOrLoseRate = profitOrLoseRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockHistory that = (StockHistory) o;
        return Objects.equals(memberStock.getId(), that.memberStock.getId()) &&
            Objects.equals(currentPriceInWon.stripTrailingZeros(), that.currentPriceInWon.stripTrailingZeros()) &&
            Objects.equals(currentPriceInDollar.stripTrailingZeros(), that.currentPriceInDollar.stripTrailingZeros()) &&
            Objects.equals(profitOrLoseRate.stripTrailingZeros(), that.profitOrLoseRate.stripTrailingZeros());
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

}

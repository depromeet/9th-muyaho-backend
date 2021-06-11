package com.depromeet.muyaho.domain.domain.dailystockamount;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    indexes = @Index(name = "idx_daily_stock_amount_1", columnList = "memberId,localDateTime")
)
public class DailyStockAmount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    private BigDecimal finalAsset;

    private BigDecimal seedAmount;

    private BigDecimal finalProfitOrLoseRate;

    @Builder
    public DailyStockAmount(Long memberId, LocalDateTime localDateTime, BigDecimal finalAsset, BigDecimal seedAmount, BigDecimal finalProfitOrLoseRate) {
        this.memberId = memberId;
        this.localDateTime = localDateTime;
        this.finalAsset = finalAsset;
        this.seedAmount = seedAmount;
        this.finalProfitOrLoseRate = finalProfitOrLoseRate;
    }

}

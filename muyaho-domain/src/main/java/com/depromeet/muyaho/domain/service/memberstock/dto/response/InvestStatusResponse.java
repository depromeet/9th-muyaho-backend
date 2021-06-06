package com.depromeet.muyaho.domain.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.domain.dailystockamount.DailyStockAmount;
import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.calculateDifferencePercent;
import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvestStatusResponse {

    private String todayProfitOrLose;

    private String finalAsset;
    private String seedAmount;
    private String finalProfitOrLoseRate;

    private OverviewStocksResponse overview;

    private InvestStatusResponse(DailyStockAmount dailyStockAmount, List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        this.overview = OverviewStocksResponse.of(bitCoinCurrentInfo, domesticCurrentInfo, foreignStocks);
        final BigDecimal seedAmount = calculateSeedAmount(bitCoinCurrentInfo, domesticCurrentInfo, foreignStocks);
        final BigDecimal finalAsset = calculateFinalAsset(bitCoinCurrentInfo, domesticCurrentInfo, foreignStocks);
        this.finalAsset = roundFloor(finalAsset);
        this.seedAmount = roundFloor(seedAmount);
        this.finalProfitOrLoseRate = roundFloor(calculateDifferencePercent(finalAsset, seedAmount));
        this.todayProfitOrLose = roundFloor(calculateTodayProfitOrLose(finalAsset, dailyStockAmount));
    }

    public static InvestStatusResponse of(DailyStockAmount dailyStockAmount, List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> overSeasCurrentInfo) {
        return new InvestStatusResponse(dailyStockAmount, bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
    }

    private BigDecimal calculateTodayProfitOrLose(BigDecimal finalAsset, DailyStockAmount dailyStockAmount) {
        if (dailyStockAmount == null) {
            return finalAsset;
        }
        return finalAsset.subtract(dailyStockAmount.getFinalAsset());
    }

    private BigDecimal calculateSeedAmount(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> overSeasCurrentInfo) {
        return sum(Stream.concat(Stream.concat(
            bitCoinCurrentInfo.stream()
                .map(StockCalculateResponse::takePurchaseAmountPrice),
            domesticCurrentInfo.stream()
                .map(StockCalculateResponse::takePurchaseAmountPrice)),
            overSeasCurrentInfo.stream()
                .map(StockCalculateResponse::takePurchaseAmountInWon)
        ));
    }

    private BigDecimal calculateFinalAsset(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> overSeasCurrentInfo) {
        return sum(Stream.concat(Stream.concat(
            bitCoinCurrentInfo.stream()
                .map(StockCalculateResponse::takeCurrentAmountPrice),
            domesticCurrentInfo.stream()
                .map(StockCalculateResponse::takeCurrentAmountPrice)),
            overSeasCurrentInfo.stream()
                .map(StockCalculateResponse::takeCurrentAmountPrice)
        ));
    }

    private BigDecimal sum(Stream<BigDecimal> bigDecimalStream) {
        return bigDecimalStream
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public DailyStockAmount toDailyStockAmount(Long memberId, LocalDateTime localDateTime) {
        return DailyStockAmount.builder()
            .memberId(memberId)
            .localDateTime(localDateTime)
            .finalAsset(new BigDecimal(finalAsset))
            .seedAmount(new BigDecimal(seedAmount))
            .finalProfitOrLoseRate(new BigDecimal(finalProfitOrLoseRate))
            .build();
    }

}

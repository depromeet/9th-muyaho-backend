package com.depromeet.muyaho.api.service.memberstock.dto.response;

import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.calculateDifferencePercent;
import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
public class InvestStatusResponse {

    // TODO 오늘의 수익금
    private final String todayEarnings = "-1";

    private final String finalAsset;
    private final String seedAmount;
    private final String finalEarningRate;

    private final List<StockCalculateResponse> bitCoins = new ArrayList<>();
    private final List<StockCalculateResponse> domesticStocks = new ArrayList<>();
    private final List<StockCalculateResponse> overSeasStocks = new ArrayList<>();

    private InvestStatusResponse(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> overSeasCurrentInfo) {
        this.bitCoins.addAll(bitCoinCurrentInfo);
        this.domesticStocks.addAll(domesticCurrentInfo);
        this.overSeasStocks.addAll(overSeasCurrentInfo);
        final BigDecimal seedAmount = calculateSeed(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
        final BigDecimal finalAsset = calculateFinalAsset(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
        this.finalAsset = roundFloor(finalAsset);
        this.seedAmount = roundFloor(seedAmount);
        this.finalEarningRate = roundFloor(calculateDifferencePercent(finalAsset, seedAmount));
    }

    public static InvestStatusResponse of(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> overSeasCurrentInfo) {
        return new InvestStatusResponse(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
    }

    private BigDecimal calculateSeed(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> overSeasCurrentInfo) {
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

}

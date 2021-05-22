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
    private final String todayEarnings = "99999999999";

    private final BigDecimal finalAsset;
    private final BigDecimal seedAmount;
    private final String finalEarningRate;

    private final List<StockCalculateResponse> bitCoins = new ArrayList<>();
    private final List<StockCalculateResponse> domesticStocks = new ArrayList<>();
    private final List<OverSeaCalculateResponse> overSeasStocks = new ArrayList<>();

    private InvestStatusResponse(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        this.bitCoins.addAll(bitCoinCurrentInfo);
        this.domesticStocks.addAll(domesticCurrentInfo);
        this.overSeasStocks.addAll(overSeasCurrentInfo);
        this.seedAmount = calculateSeed(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
        this.finalAsset = calculateFinalAsset(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
        this.finalEarningRate = roundFloor(calculateDifferencePercent(finalAsset, seedAmount));
    }

    public static InvestStatusResponse of(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        return new InvestStatusResponse(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
    }

    private BigDecimal calculateSeed(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        return sum(Stream.concat(Stream.concat(
            bitCoinCurrentInfo.stream()
                .map(StockCalculateResponse::takePurchaseAmountPrice),
            domesticCurrentInfo.stream()
                .map(StockCalculateResponse::takePurchaseAmountPrice)),
            overSeasCurrentInfo.stream()
                .map(OverSeaCalculateResponse::takePurchaseAmountPriceInWon)
        ));
    }

    private BigDecimal calculateFinalAsset(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        return sum(Stream.concat(Stream.concat(
            bitCoinCurrentInfo.stream()
                .map(StockCalculateResponse::takeCurrentAmountPrice),
            domesticCurrentInfo.stream()
                .map(StockCalculateResponse::takeCurrentAmountPrice)),
            overSeasCurrentInfo.stream()
                .map(OverSeaCalculateResponse::takeCurrentAmountPriceInWon)
        ));
    }

    private BigDecimal sum(Stream<BigDecimal> bigDecimalStream) {
        return bigDecimalStream
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

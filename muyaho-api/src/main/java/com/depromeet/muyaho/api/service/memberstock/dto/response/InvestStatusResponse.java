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

    private final BigDecimal finalAsset;
    private final BigDecimal seedAmount;
    private final String finalEarningRate;

    private final List<StockCalculateResponse> bitCoin = new ArrayList<>();
    private final List<StockCalculateResponse> domesticStock = new ArrayList<>();
    private final List<OverSeaCalculateResponse> overSeasStock = new ArrayList<>();

    private InvestStatusResponse(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        this.bitCoin.addAll(bitCoinCurrentInfo);
        this.domesticStock.addAll(domesticCurrentInfo);
        this.overSeasStock.addAll(overSeasCurrentInfo);
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
                .map(StockCalculateResponse::getPurchaseAmountPrice),
            domesticCurrentInfo.stream()
                .map(StockCalculateResponse::getPurchaseAmountPrice)),
            overSeasCurrentInfo.stream()
                .map(OverSeaCalculateResponse::getPurchaseAmountPriceInWon)
        ));
    }

    private BigDecimal calculateFinalAsset(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        return sum(Stream.concat(Stream.concat(
            bitCoinCurrentInfo.stream()
                .map(StockCalculateResponse::getCurrentAmountPrice),
            domesticCurrentInfo.stream()
                .map(StockCalculateResponse::getCurrentAmountPrice)),
            overSeasCurrentInfo.stream()
                .map(OverSeaCalculateResponse::getCurrentAmountPriceInWon)
        ));
    }

    private BigDecimal sum(Stream<BigDecimal> bigDecimalStream) {
        return bigDecimalStream
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

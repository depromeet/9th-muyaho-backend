package com.depromeet.muyaho.api.service.investstatus.dto.response;

import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.calculateDifferencePercent;
import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

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

    /**
     * TDOO 계산해주는 유틸성 클래스 만들어서 리팩토링하기..
     * 으아아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ
     */
    private BigDecimal calculateSeed(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        BigDecimal sum = bitCoinCurrentInfo.stream()
            .map(StockCalculateResponse::getPurchaseAmountPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        sum = sum.add(domesticCurrentInfo.stream()
            .map(StockCalculateResponse::getPurchaseAmountPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        return sum.add(overSeasCurrentInfo.stream()
            .map(OverSeaCalculateResponse::getPurchaseAmountPriceInWon)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private BigDecimal calculateFinalAsset(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        BigDecimal sum = bitCoinCurrentInfo.stream()
            .map(StockCalculateResponse::getCurrentAmountPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        sum = sum.add(domesticCurrentInfo.stream()
            .map(StockCalculateResponse::getCurrentAmountPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        return sum.add(overSeasCurrentInfo.stream()
            .map(OverSeaCalculateResponse::getCurrentAmountPriceInWon)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public static InvestStatusResponse of(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<OverSeaCalculateResponse> overSeasCurrentInfo) {
        return new InvestStatusResponse(bitCoinCurrentInfo, domesticCurrentInfo, overSeasCurrentInfo);
    }

}

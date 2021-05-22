package com.depromeet.muyaho.api.service.investstatus;

import com.depromeet.muyaho.api.service.investstatus.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.api.service.investstatus.dto.response.OverSeaCalculateResponse;
import com.depromeet.muyaho.api.service.memberstock.MemberStockRetrieveService;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.external.client.currency.CurrencyRateApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InvestStatusService {

    private final CurrencyRateApiCaller currencyRateApiCaller;
    private final MemberStockRetrieveService memberStockRetrieveService;

    public InvestStatusResponse getInvestStatusResponse(Long memberId) {
        final BigDecimal rate = currencyRateApiCaller.getCurrencyRate().getUSDToKRWExchangeRate();
        List<StockCalculateResponse> bitCoinCurrentInfo = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);
        List<StockCalculateResponse> domesticCurrentInfo = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.DOMESTIC_STOCK, memberId);
        List<OverSeaCalculateResponse> overSeas = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.OVERSEAS_STOCK, memberId).stream()
            .map(dollar -> OverSeaCalculateResponse.of(dollar, dollar.getCurrentUnitPrice().multiply(rate)))
            .collect(Collectors.toList());
        return InvestStatusResponse.of(bitCoinCurrentInfo, domesticCurrentInfo, overSeas);
    }

}

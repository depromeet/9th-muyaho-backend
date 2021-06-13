package com.depromeet.muyaho.api.controller.currency;

import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.external.client.currency.ExchangeRateApiCaller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class CurrencyController {

    private final ExchangeRateApiCaller exchangeRateApiCaller;

    @Operation(summary = "실시간으로 달러에서 원화로 환율을 조회하는 API")
    @GetMapping("/api/v1/exchange/rate")
    public ApiResponse<BigDecimal> getExchangeRate() {
        return ApiResponse.success(exchangeRateApiCaller.fetchExchangeRate());
    }

}

package com.depromeet.muyaho.api.controller.stock;

import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.service.stock.StockService;
import com.depromeet.muyaho.domain.service.stock.dto.response.StockInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stockService;

    @Operation(summary = "주식 종목 코드와 종목명을 조회하는 API", description = "DOMESTIC_STOCK, OVERSEAS_STOCK, BITCOIN")
    @GetMapping("/api/v1/stock/list")
    public ApiResponse<List<StockInfoResponse>> retrieveStocks(@RequestParam StockMarketType type) {
        return ApiResponse.success(stockService.retrieveStockInfo(type));
    }

}

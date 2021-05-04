package com.depromeet.muyaho.controller.stock;

import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.service.stock.StockService;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
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

    @Operation(summary = "비트코인 종목 코드/명을 조회하는 API")
    @GetMapping("/api/v1/stock/list")
    public ApiResponse<List<StockInfoResponse>> retrieveStocks(@RequestParam StockMarketType type) {
        return ApiResponse.success(stockService.retrieveStockInfo(type));
    }

}

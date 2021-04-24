package com.depromeet.muyaho.controller.stock;

import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.service.stock.StockBatchService;
import com.depromeet.muyaho.service.stock.StockService;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockBatchService stockBatchService;
    private final StockService stockService;

    @GetMapping("/api/v1/renew/bitcoin")
    public ApiResponse<String> refreshBitCoinStock() {
        stockBatchService.renewBitCoin();
        return ApiResponse.SUCCESS;
    }

    @GetMapping("/api/v1/stock/list")
    public ApiResponse<List<StockInfoResponse>> retrieveStocks(@RequestParam StockMarketType type) {
        return ApiResponse.success(stockService.retrieveStockInfo(type));
    }

}

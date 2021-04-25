package com.depromeet.muyaho.controller.stock;

import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.service.stock.StockBatchService;
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

    private final StockBatchService stockBatchService;
    private final StockService stockService;

    @Operation(summary = "비트코인 주식 종목 리스트를 갱신하는 API", description = "차후 관리자 서버에 둘 예정")
    @GetMapping("/api/v1/renew/bitcoin")
    public ApiResponse<String> refreshBitCoinStock() {
        stockBatchService.renewBitCoin();
        return ApiResponse.SUCCESS;
    }

    @Operation(summary = "비트코인 종목 코드/명을 조회하는 API")
    @GetMapping("/api/v1/stock/list")
    public ApiResponse<List<StockInfoResponse>> retrieveStocks(@RequestParam StockMarketType type) {
        return ApiResponse.success(stockService.retrieveStockInfo(type));
    }

}

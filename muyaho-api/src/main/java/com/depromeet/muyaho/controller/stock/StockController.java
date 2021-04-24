package com.depromeet.muyaho.controller.stock;

import com.depromeet.muyaho.config.interceptor.Auth;
import com.depromeet.muyaho.config.resolver.MemberId;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.stock.dto.request.AddStockRequest;
import com.depromeet.muyaho.service.stock.StockService;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockController {

    private final StockService stockService;

    @Auth
    @PostMapping("/api/v1/stock")
    public ApiResponse<String> addStock(@Valid @RequestBody AddStockRequest request, @MemberId Long memberId) {
        stockService.addStock(request, memberId);
        return ApiResponse.SUCCESS;
    }

    @Auth
    @GetMapping("/api/v1/stock/list")
    public ApiResponse<List<StockInfoResponse>> retrieveStocks(@MemberId Long memberId) {
        return ApiResponse.success(stockService.retrieveMyStocks(memberId));
    }

}

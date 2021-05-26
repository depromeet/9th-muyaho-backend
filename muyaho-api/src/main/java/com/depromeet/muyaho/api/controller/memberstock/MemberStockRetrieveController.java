package com.depromeet.muyaho.api.controller.memberstock;

import com.depromeet.muyaho.api.config.interceptor.Auth;
import com.depromeet.muyaho.api.config.resolver.MemberId;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.service.memberstock.MemberStockRetrieveService;
import com.depromeet.muyaho.api.service.memberstock.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberStockRetrieveController {

    private final MemberStockRetrieveService memberStockRetrieveService;

    @Operation(summary = "내가 보유한 주식들을 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/member/stock")
    public ApiResponse<List<StockCalculateResponse>> getStocksInfo(@RequestParam StockMarketType type, @MemberId Long memberId) {
        return ApiResponse.success(memberStockRetrieveService.getMemberCurrentStocks(type, memberId));
    }

    @Operation(summary = "내가 보유한 주식 전체를 조회 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/member/stock/status")
    public ApiResponse<InvestStatusResponse> getMemberInvestStatus(@MemberId Long memberId) {
        return ApiResponse.success(memberStockRetrieveService.getMemberInvestStatus(memberId));
    }

    @Operation(summary = "내가 보유한 주식 전체를 조회 API (이전 캐시)", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/member/stock/status/previous")
    public ApiResponse<InvestStatusResponse> getMemberPreviousInvestStatus(@MemberId Long memberId) {
        return ApiResponse.success(memberStockRetrieveService.getMemberPreviousInvestStatus(memberId));
    }

}

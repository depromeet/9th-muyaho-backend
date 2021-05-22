package com.depromeet.muyaho.api.controller.memberstock;

import com.depromeet.muyaho.api.config.interceptor.Auth;
import com.depromeet.muyaho.api.config.resolver.MemberId;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.service.memberstock.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.api.service.memberstock.MemberStockRetrieveService;
import com.depromeet.muyaho.api.service.memberstock.MemberStockService;
import com.depromeet.muyaho.api.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.api.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.api.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.api.service.memberstock.dto.response.MemberStockInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberStockController {

    private final MemberStockService memberStockService;
    private final MemberStockRetrieveService memberStockRetrieveService;

    @Operation(summary = "내가 보유한 주식을 등록하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization", description = "Bearer Token"))
    @Auth
    @PostMapping("/api/v1/member/stock")
    public ApiResponse<MemberStockInfoResponse> addMemberStock(@Valid @RequestBody AddMemberStockRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberStockService.addMemberStock(request, memberId));
    }

    @Operation(summary = "내가 보유한 주식들을 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/member/stock")
    public ApiResponse<List<StockCalculateResponse>> getStocksInfo(@RequestParam StockMarketType type, @MemberId Long memberId) {
        return ApiResponse.success(memberStockRetrieveService.getMemberCurrentStocks(type, memberId));
    }

    @Operation(summary = "내가 보유한 주식 전체를 조회 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/stock/status")
    public ApiResponse<InvestStatusResponse> getAllStocksStatus(@MemberId Long memberId) {
        return ApiResponse.success(memberStockRetrieveService.getAllStockStatus(memberId));
    }

    @Operation(summary = "내가 보유한 주식을 수정하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @PutMapping("/api/v1/member/stock")
    public ApiResponse<MemberStockInfoResponse> updateMemberStock(@Valid @RequestBody UpdateMemberStockRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberStockService.updateMemberStock(request, memberId));
    }

    @Operation(summary = "내가 보유한 주식을 삭제하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @DeleteMapping("/api/v1/member/stock")
    public ApiResponse<String> deleteMemberStock(@Valid DeleteMemberStockRequest request, @MemberId Long memberId) {
        memberStockService.deleteMemberStock(request, memberId);
        return ApiResponse.SUCCESS;
    }

}

package com.depromeet.muyaho.controller.memberstock;

import com.depromeet.muyaho.config.interceptor.Auth;
import com.depromeet.muyaho.config.resolver.MemberId;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.memberstock.MemberStockService;
import com.depromeet.muyaho.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.service.memberstock.dto.response.MemberStockInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberStockController {

    private final MemberStockService memberStockService;

    @Operation(summary = "내가 소유한 주식을 새롭게 등록하는 API", description = "로그인 필요")
    @Auth
    @PostMapping("/api/v1/member/stock")
    public ApiResponse<MemberStockInfoResponse> addMemberStock(@Valid @RequestBody AddMemberStockRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberStockService.addMemberStock(request, memberId));
    }

    @Operation(summary = "내가 소유한 주식들을 조회하는 API", description = "로그인 필요")
    @Auth
    @GetMapping("/api/v1/member/stock")
    public ApiResponse<List<MemberStockInfoResponse>> getMyStocks(@MemberId Long memberId) {
        return ApiResponse.success(memberStockService.getMyStockInfos(memberId));
    }

    @Operation(summary = "내가 소유한 주식들을 수정하는 API", description = "로그인 필요")
    @Auth
    @PutMapping("/api/v1/member/stock")
    public ApiResponse<MemberStockInfoResponse> updateMemberStock(@Valid @RequestBody UpdateMemberStockRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberStockService.updateMemberStock(request, memberId));
    }

}

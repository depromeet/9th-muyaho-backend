package com.depromeet.muyaho.api.controller.memberstock;

import com.depromeet.muyaho.api.config.interceptor.Auth;
import com.depromeet.muyaho.api.config.resolver.MemberId;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.domain.service.memberstock.MemberStockService;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.response.MemberStockInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberStockController {

    private final MemberStockService memberStockService;

    @Operation(summary = "내가 보유한 주식을 등록하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization", description = "Bearer Token"))
    @Auth
    @PostMapping("/api/v1/member/stock")
    public ApiResponse<MemberStockInfoResponse> addMemberStock(@Valid @RequestBody AddMemberStockRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberStockService.addMemberStock(request, memberId));
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

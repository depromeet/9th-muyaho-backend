package com.depromeet.muyaho.api.controller.investstatus;

import com.depromeet.muyaho.api.config.interceptor.Auth;
import com.depromeet.muyaho.api.config.resolver.MemberId;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.service.investstatus.InvestStatusService;
import com.depromeet.muyaho.api.service.investstatus.dto.response.InvestStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class InvestStatusController {

    private final InvestStatusService investStatusService;

    @Operation(summary = "내가 보유한 주식 전체 조회 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/invest")
    public ApiResponse<InvestStatusResponse> getInvestStatus(@MemberId Long memberId) {
        return ApiResponse.success(investStatusService.getInvestStatusResponse(memberId));
    }

}

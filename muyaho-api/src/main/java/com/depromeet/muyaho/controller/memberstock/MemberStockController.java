package com.depromeet.muyaho.controller.memberstock;

import com.depromeet.muyaho.config.interceptor.Auth;
import com.depromeet.muyaho.config.resolver.MemberId;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.memberstock.MemberStockService;
import com.depromeet.muyaho.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.service.memberstock.dto.response.MemberStockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberStockController {

    private final MemberStockService memberStockService;

    @Auth
    @PostMapping("/api/v1/member/stock")
    public ApiResponse<MemberStockInfoResponse> addMemberStock(@Valid @RequestBody AddMemberStockRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberStockService.addMemberStock(request, memberId));
    }

}

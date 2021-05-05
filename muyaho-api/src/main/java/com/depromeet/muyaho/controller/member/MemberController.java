package com.depromeet.muyaho.controller.member;

import com.depromeet.muyaho.config.interceptor.Auth;
import com.depromeet.muyaho.config.resolver.MemberId;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.member.MemberService;
import com.depromeet.muyaho.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.service.member.dto.response.MemberInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "나의 회원 정보를 조회하는 API", description = "Authorization 헤더가 필요합니다", security = {@SecurityRequirement(name = "bearer-key")})
    @Auth
    @GetMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> getMyMemberInfo(@MemberId Long memberId) {
        return ApiResponse.success(memberService.getMemberInfo(memberId));
    }

    @Operation(summary = "나의 회원 정보를 수정하는 API", description = "Authorization 헤더가 필요합니다", security = {@SecurityRequirement(name = "bearer-key")})
    @Auth
    @PutMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> updateMemberInfo(@Valid @RequestBody UpdateMemberRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberService.updateMemberInfo(request, memberId));
    }

    @Operation(summary = "회원탈퇴 API", description = "Authorization 헤더가 필요합니다", security = {@SecurityRequirement(name = "bearer-key")})
    @Auth
    @DeleteMapping("/api/v1/member")
    public ApiResponse<String> deleteMemberInfo(@MemberId Long memberId) {
        memberService.deleteMemberInfo(memberId);
        return ApiResponse.SUCCESS;
    }

}

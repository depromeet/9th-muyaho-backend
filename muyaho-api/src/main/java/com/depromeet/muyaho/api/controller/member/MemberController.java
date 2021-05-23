package com.depromeet.muyaho.api.controller.member;

import com.depromeet.muyaho.api.config.interceptor.Auth;
import com.depromeet.muyaho.api.config.resolver.MemberId;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.service.member.dto.request.CheckDuplicateNameRequest;
import com.depromeet.muyaho.api.service.member.MemberService;
import com.depromeet.muyaho.api.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.api.service.member.dto.response.MemberInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "닉네임 중복 체크 API")
    @GetMapping("/api/v1/check/name")
    public ApiResponse<String> checkDuplicateName(@Valid CheckDuplicateNameRequest request) {
        memberService.checkDuplicateName(request);
        return ApiResponse.SUCCESS;
    }

    @Operation(summary = "나의 회원 정보를 조회하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @GetMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> getMyMemberInfo(@MemberId Long memberId) {
        return ApiResponse.success(memberService.getMemberInfo(memberId));
    }

    @Operation(summary = "나의 회원 정보를 수정하는 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @PutMapping("/api/v1/member")
    public ApiResponse<MemberInfoResponse> updateMemberInfo(@Valid @RequestBody UpdateMemberRequest request, @MemberId Long memberId) {
        return ApiResponse.success(memberService.updateMemberInfo(request, memberId));
    }

    @Operation(summary = "회원탈퇴 API", security = {@SecurityRequirement(name = "Authorization")}, parameters = @Parameter(name = "Authorization"))
    @Auth
    @DeleteMapping("/api/v1/member")
    public ApiResponse<String> deleteMemberInfo(@MemberId Long memberId) {
        memberService.deleteMemberInfo(memberId);
        return ApiResponse.SUCCESS;
    }

}

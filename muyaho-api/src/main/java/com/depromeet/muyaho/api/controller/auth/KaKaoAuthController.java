package com.depromeet.muyaho.api.controller.auth;

import com.depromeet.muyaho.api.config.session.MemberSession;
import com.depromeet.muyaho.api.config.session.SessionConstants;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.domain.service.auth.AuthService;
import com.depromeet.muyaho.domain.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.domain.service.auth.dto.request.SignUpRequest;
import com.depromeet.muyaho.domain.service.auth.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class KaKaoAuthController {

    private final AuthService kaKaoAuthService;
    private final HttpSession httpSession;

    @Operation(summary = "카카오 로그인 요청 API")
    @PostMapping("/api/v1/login/kakao")
    public ApiResponse<AuthResponse> handleAppleAuthenticationWithToken(@Valid @RequestBody LoginRequest request) {
        Long memberId = kaKaoAuthService.login(request);
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(memberId));
        return ApiResponse.success(AuthResponse.of(httpSession.getId()));
    }

    @Operation(summary = "카카오 회원가입 요청 API")
    @PostMapping("/api/v1/signup/kakao")
    public ApiResponse<AuthResponse> signUpMember(@Valid @RequestBody SignUpRequest request) {
        Long memberId = kaKaoAuthService.signUp(request);
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(memberId));
        return ApiResponse.success(AuthResponse.of(httpSession.getId()));
    }

}

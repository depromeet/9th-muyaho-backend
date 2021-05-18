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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AppleAuthController {

    private final AuthService appleAuthService;
    private final HttpSession httpSession;

    @Operation(summary = "애플 로그인 요청 API")
    @PostMapping("/api/v1/login/apple")
    public ApiResponse<AuthResponse> handleAppleAuthenticationWithToken(@Valid @RequestBody LoginRequest request) {
        Long memberId = appleAuthService.login(request);
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(memberId));
        return ApiResponse.success(AuthResponse.of(httpSession.getId()));
    }

    @Operation(summary = "애플 회원가입 요청 API")
    @PostMapping("/api/v1/signup/apple")
    public ApiResponse<AuthResponse> signUpMember(@Valid @RequestBody SignUpRequest request) {
        Long memberId = appleAuthService.signUp(request);
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(memberId));
        return ApiResponse.success(AuthResponse.of(httpSession.getId()));
    }

}

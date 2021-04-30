package com.depromeet.muyaho.controller.auth;

import com.depromeet.muyaho.config.session.MemberSession;
import com.depromeet.muyaho.config.session.SessionConstants;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.auth.AuthService;
import com.depromeet.muyaho.service.auth.dto.request.AuthRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignupMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final HttpSession httpSession;

    @PostMapping("/api/v1/login")
    public ApiResponse<String> handleAppleAuthenticationWithToken(@Valid @RequestBody AuthRequest request) {
        Long memberId = authService.handleAuthentication(request);
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(memberId));
        return ApiResponse.success(httpSession.getId());
    }

    @PostMapping("/api/v1/signup")
    public ApiResponse<String> signUpMember(@Valid @RequestBody SignupMemberRequest request) {
        Long memberId = authService.signUpMember(request);
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(memberId));
        return ApiResponse.success(httpSession.getId());
    }

}

package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignUpRequest;

public interface AuthService {

    MemberProvider getProvider();

    Long login(LoginRequest request);

    Long signUp(SignUpRequest request);

}

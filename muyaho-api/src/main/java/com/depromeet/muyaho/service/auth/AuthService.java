package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignUpRequest;

public interface AuthService {

    Long login(LoginRequest request);

    Long signUp(SignUpRequest request);

}

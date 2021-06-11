package com.depromeet.muyaho.api.service.auth;

import com.depromeet.muyaho.api.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.api.service.auth.dto.request.SignUpRequest;

public interface AuthService {

    Long login(LoginRequest request);

    Long signUp(SignUpRequest request);

}

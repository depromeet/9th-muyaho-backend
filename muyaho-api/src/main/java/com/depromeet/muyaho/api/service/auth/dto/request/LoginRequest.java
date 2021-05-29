package com.depromeet.muyaho.api.service.auth.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotBlank
    private String token;

    public static LoginRequest testInstance(String token) {
        return new LoginRequest(token);
    }

}

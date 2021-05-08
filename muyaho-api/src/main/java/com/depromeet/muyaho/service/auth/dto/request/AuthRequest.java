package com.depromeet.muyaho.service.auth.dto.request;

import com.depromeet.muyaho.domain.member.MemberProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthRequest {

    @NotBlank
    private String token;

    @NotNull
    private MemberProvider provider;

    public static AuthRequest testInstance(String token, MemberProvider provider) {
        return new AuthRequest(token, provider);
    }

    public boolean isAppleType() {
        return provider.equals(MemberProvider.APPLE);
    }

    public boolean isKaKaoType() {
        return provider.equals(MemberProvider.KAKAO);
    }

}

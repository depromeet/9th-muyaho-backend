package com.depromeet.muyaho.service.auth.dto.request;

import com.depromeet.muyaho.domain.member.MemberProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupMemberRequest {

    @NotBlank
    private String token;

    @NotBlank
    private String name;

    private String profileUrl;

    @NotNull
    private MemberProvider provider;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public SignupMemberRequest(String token, String name, String profileUrl, MemberProvider provider) {
        this.token = token;
        this.name = name;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public boolean isAppleType() {
        return provider.equals(MemberProvider.APPLE);
    }

    public boolean isKaKaoType() {
        return provider.equals(MemberProvider.KAKAO);
    }

}

package com.depromeet.muyaho.service.auth.dto.request;

import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.service.member.dto.request.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

    @NotBlank
    private String token;

    @NotBlank
    private String name;

    private String profileUrl;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public SignUpRequest(String token, String name, String profileUrl) {
        this.token = token;
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public CreateMemberRequest toCreateMemberRequest(String uid, String email, MemberProvider provider) {
        return CreateMemberRequest.builder()
            .uid(uid)
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();
    }

}

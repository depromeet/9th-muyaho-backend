package com.depromeet.muyaho.service.member.dto.request;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpMemberRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    private String profileUrl;

    private MemberProvider provider;

    public Member toEntity() {
        return Member.newInstance(email, name, profileUrl, provider);
    }

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public SignUpMemberRequest(String email, String name, String profileUrl, MemberProvider provider) {
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

}

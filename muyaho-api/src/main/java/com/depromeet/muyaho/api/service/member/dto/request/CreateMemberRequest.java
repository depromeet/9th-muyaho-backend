package com.depromeet.muyaho.api.service.member.dto.request;

import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemberRequest {

    @NotBlank
    private String uid;

    private String email;

    @NotBlank
    private String name;

    private String profileUrl;

    @NotNull
    private MemberProvider provider;

    @Builder
    public CreateMemberRequest(String uid, String email, String name, String profileUrl, MemberProvider provider) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public Member toEntity() {
        return Member.newInstance(uid, email, name, profileUrl, provider);
    }

}

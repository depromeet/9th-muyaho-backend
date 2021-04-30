package com.depromeet.muyaho.service.auth.dto.request;

import com.depromeet.muyaho.domain.member.MemberProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthRequest {

    @NotBlank
    private String token;

    @NotNull
    private MemberProvider provider;

}

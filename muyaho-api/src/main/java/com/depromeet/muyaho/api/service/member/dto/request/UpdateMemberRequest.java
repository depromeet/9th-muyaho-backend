package com.depromeet.muyaho.api.service.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberRequest {

    @NotBlank(message = "{member.name.notBlank}")
    private String name;

    private String profileUrl;

    public static UpdateMemberRequest testInstance(String name, String profileUrl) {
        return new UpdateMemberRequest(name, profileUrl);
    }

}

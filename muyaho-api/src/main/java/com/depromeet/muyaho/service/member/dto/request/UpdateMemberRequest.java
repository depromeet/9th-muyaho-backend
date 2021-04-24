package com.depromeet.muyaho.service.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberRequest {

    private String name;

    private String profileUrl;

    public static UpdateMemberRequest testInstance(String name, String profileUrl) {
        return new UpdateMemberRequest(name, profileUrl);
    }

}

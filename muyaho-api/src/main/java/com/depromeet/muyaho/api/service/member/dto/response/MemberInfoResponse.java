package com.depromeet.muyaho.api.service.member.dto.response;

import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResponse {

    private String name;

    private String profileUrl;

    private MemberProvider provider;

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getName(), member.getProfileUrl(), member.getProvider());
    }

}

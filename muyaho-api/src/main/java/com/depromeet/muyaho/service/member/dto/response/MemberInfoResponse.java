package com.depromeet.muyaho.service.member.dto.response;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

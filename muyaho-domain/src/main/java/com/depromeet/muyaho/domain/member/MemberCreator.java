package com.depromeet.muyaho.domain.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    public static Member create(String email) {
        return Member.newInstance(email, "테스트", null);
    }

    public static Member create(String email, String name, String profileUrl) {
        return Member.newInstance(email, name, profileUrl);
    }

}

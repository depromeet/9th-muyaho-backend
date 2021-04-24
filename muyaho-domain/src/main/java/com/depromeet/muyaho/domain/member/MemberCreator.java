package com.depromeet.muyaho.domain.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    public static Member create(String uid, String name, String profileUrl, MemberProvider provider) {
        return Member.newInstance(uid, name, profileUrl, provider);
    }

}

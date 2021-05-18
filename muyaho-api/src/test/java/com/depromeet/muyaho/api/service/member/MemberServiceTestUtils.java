package com.depromeet.muyaho.api.service.member;

import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTestUtils {

    public static void assertMember(Member member, String uid, String email, String name, String profileUrl, MemberProvider provider) {
        assertThat(member.getUid()).isEqualTo(uid);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(member.getProvider()).isEqualTo(provider);
    }

}

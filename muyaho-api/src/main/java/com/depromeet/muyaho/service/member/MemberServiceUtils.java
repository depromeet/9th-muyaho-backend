package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MemberServiceUtils {

    static void validateNotExistEmail(MemberRepository memberRepository, String email) {
        Member findMember = memberRepository.findMemberByEmail(email);
        if (findMember != null) {
            throw new IllegalArgumentException(String.format("이미 존재하는 이메일 (%s) 입니다", email));
        }
    }

}

package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MemberServiceUtils {

    static void validateNotExistEmail(MemberRepository memberRepository, String email, MemberProvider provider) {
        Member findMember = memberRepository.findMemberByEmailAndProvider(email, provider);
        if (findMember != null) {
            throw new IllegalArgumentException(String.format("이미 존재하는 이메일 (%s) 입니다", email));
        }
    }

    public static Member findMemberById(MemberRepository memberRepository, Long memberId) {
        Member findMember = memberRepository.findMemberById(memberId);
        if (findMember == null) {
            throw new IllegalArgumentException(String.format("존재하지 않는 멤버 (%s) 입니다", memberId));
        }
        return findMember;
    }

}

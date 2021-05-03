package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.exception.ConflictException;
import com.depromeet.muyaho.exception.NotFoundException;
import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceUtils {

    static Member findMemberById(MemberRepository memberRepository, Long memberId) {
        Member findMember = memberRepository.findMemberById(memberId);
        if (findMember == null) {
            throw new NotFoundException(String.format("존재하지 않는 멤버 (%s) 입니다", memberId));
        }
        return findMember;
    }

    public static void validateNotExistName(MemberRepository memberRepository, String name) {
        Member findMember = memberRepository.findMemberByName(name);
        if (findMember != null) {
            throw new ConflictException(String.format("이미 존재하는 닉네임 (%s) 입니다", name));
        }
    }

}

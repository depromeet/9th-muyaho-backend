package com.depromeet.muyaho.api.service.member;

import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import com.depromeet.muyaho.common.exception.ConflictException;
import com.depromeet.muyaho.common.exception.NotFoundException;
import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberRepository;
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

    public static Member findMemberByUidAndProvider(MemberRepository memberRepository, String uid, MemberProvider provider) {
        Member member = memberRepository.findMemberByUidAndProvider(uid, provider);
        if (member == null) {
            throw new NotFoundException(String.format("존재하지 않는 회원 uid: (%s - %s) 입니다. 회원가입이 필요합니다", provider, uid));
        }
        return member;
    }

    public static void validateNotExistName(MemberRepository memberRepository, String name) {
        Member findMember = memberRepository.findMemberByName(name);
        if (findMember != null) {
            throw new ConflictException(String.format("이미 존재하는 닉네임 (%s) 입니다", name));
        }
    }

    public static void validateNotExistMember(MemberRepository memberRepository, String uid, MemberProvider provider) {
        Member member = memberRepository.findMemberByUidAndProvider(uid, provider);
        if (member != null) {
            throw new ConflictException(String.format("이미 존재하는 멤버 (%s - %s) 입니다", uid, provider));
        }
    }

}

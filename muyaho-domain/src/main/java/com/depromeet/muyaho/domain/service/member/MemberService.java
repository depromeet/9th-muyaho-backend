package com.depromeet.muyaho.domain.service.member;

import com.depromeet.muyaho.domain.domain.member.DeleteMemberRepository;
import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberRepository;
import com.depromeet.muyaho.domain.service.member.dto.request.CheckDuplicateNameRequest;
import com.depromeet.muyaho.domain.service.member.dto.request.CreateMemberRequest;
import com.depromeet.muyaho.domain.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.domain.service.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final DeleteMemberRepository deleteMemberRepository;

    @Transactional
    public Long createMember(CreateMemberRequest request) {
        MemberServiceUtils.validateNotExistMember(memberRepository, request.getUid(), request.getProvider());
        MemberServiceUtils.validateNotExistName(memberRepository, request.getName());
        return memberRepository.save(request.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public void checkIsDuplicateName(CheckDuplicateNameRequest request) {
        MemberServiceUtils.validateNotExistName(memberRepository, request.getName());
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        return MemberInfoResponse.of(member);
    }

    @Transactional
    public MemberInfoResponse updateMemberInfo(UpdateMemberRequest request, Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        MemberServiceUtils.validateNotExistName(memberRepository, request.getName());
        member.updateMemberInfo(request.getName(), request.getProfileUrl());
        return MemberInfoResponse.of(member);
    }

    @Transactional
    public void deleteMemberInfo(Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        deleteMemberRepository.save(member.delete());
        memberRepository.delete(member);
    }

}

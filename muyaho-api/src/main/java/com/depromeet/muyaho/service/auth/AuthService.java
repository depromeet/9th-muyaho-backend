package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.external.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.depromeet.muyaho.service.auth.dto.request.SignupMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AppleTokenDecoder appleTokenDecoder;
    private final MemberRepository memberRepository;

    public Long handleAppleAuthentication(String idToken) {
        IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(idToken);
        Member member = memberRepository.findMemberByUidAndProvider(payload.getSub(), MemberProvider.APPLE);
        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다. 회원가입이 필요합니다");
        }
        return member.getId();
    }

    public Long signUpMember(SignupMemberRequest request) {
        if (request.isAppleType()) {
            return signUpAppleMember(request.getToken(), request.getName(), request.getProfileUrl());
        }
        throw new IllegalArgumentException(String.format("잘못된 소셜 타입 (%s) 입니다.", request.getProvider()));
    }

    private Long signUpAppleMember(String token, String name, String profileUrl) {
        IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(token);
        Member member = memberRepository.findMemberByUidAndProvider(payload.getSub(), MemberProvider.APPLE);
        if (member != null) {
            throw new IllegalArgumentException(String.format("이미 존재하는 멤버 (%s - %s) 입니다", payload.getSub(), MemberProvider.APPLE));
        }
        Member newMember = memberRepository.save(Member.newInstance(payload.getSub(), name, profileUrl, MemberProvider.APPLE));
        return newMember.getId();
    }

}

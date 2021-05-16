package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.external.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.depromeet.muyaho.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignUpRequest;
import com.depromeet.muyaho.service.member.MemberService;
import com.depromeet.muyaho.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppleAuthService implements AuthService {

    private final AppleTokenDecoder appleTokenDecoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public MemberProvider getProvider() {
        return MemberProvider.APPLE;
    }

    @Override
    public Long login(LoginRequest request) {
        IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
        return MemberServiceUtils.findMemberByUidAndProvider(memberRepository, payload.getSub(), getProvider()).getId();
    }

    @Override
    public Long signUp(SignUpRequest request) {
        IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
        return memberService.createMember(request.toCreateMemberRequest(payload.getSub(), payload.getEmail(), getProvider()));
    }

}

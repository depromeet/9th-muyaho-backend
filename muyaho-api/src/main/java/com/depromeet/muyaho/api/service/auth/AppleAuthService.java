package com.depromeet.muyaho.api.service.auth;

import com.depromeet.muyaho.api.service.member.MemberService;
import com.depromeet.muyaho.api.service.member.MemberServiceUtils;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.domain.member.MemberRepository;
import com.depromeet.muyaho.api.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.muyaho.external.client.auth.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.client.auth.apple.dto.response.IdTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppleAuthService implements AuthService {

    private static final MemberProvider provider = MemberProvider.APPLE;

    private final AppleTokenDecoder appleTokenDecoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public Long login(LoginRequest request) {
        IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
        return MemberServiceUtils.findMemberByUidAndProvider(memberRepository, payload.getSub(), provider).getId();
    }

    @Override
    public Long signUp(SignUpRequest request) {
        IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
        return memberService.createMember(request.toCreateMemberRequest(payload.getSub(), payload.getEmail(), provider));
    }

}

package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.external.auth.kakao.KaKaoApiCaller;
import com.depromeet.muyaho.external.auth.kakao.dto.response.KaKaoUserInfoResponse;
import com.depromeet.muyaho.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignUpRequest;
import com.depromeet.muyaho.service.member.MemberService;
import com.depromeet.muyaho.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KaKaoAuthService implements AuthService {

    private final KaKaoApiCaller kaKaoApiCaller;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public MemberProvider getProvider() {
        return MemberProvider.KAKAO;
    }

    @Override
    public Long login(LoginRequest request) {
        KaKaoUserInfoResponse response = kaKaoApiCaller.getKaKaoUserProfileInfo(request.getToken());
        return MemberServiceUtils.findMemberByUidAndProvider(memberRepository, response.getId(), getProvider()).getId();
    }

    @Override
    public Long signUp(SignUpRequest request) {
        KaKaoUserInfoResponse response = kaKaoApiCaller.getKaKaoUserProfileInfo(request.getToken());
        return memberService.createMember(request.toCreateMemberRequest(response.getId(), response.getEmail(), getProvider()));
    }

}

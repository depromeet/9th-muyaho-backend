package com.depromeet.muyaho.api.service.auth;

import com.depromeet.muyaho.api.service.member.MemberService;
import com.depromeet.muyaho.api.service.member.MemberServiceUtils;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.domain.member.MemberRepository;
import com.depromeet.muyaho.api.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.api.service.auth.dto.request.SignUpRequest;
import com.depromeet.muyaho.external.client.auth.kakao.KaKaoApiCaller;
import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KaKaoAuthService implements AuthService {

    private static final MemberProvider provider = MemberProvider.KAKAO;

    private final KaKaoApiCaller kaKaoApiCaller;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public Long login(LoginRequest request) {
        KaKaoProfileResponse response = kaKaoApiCaller.getProfileInfo(request.getToken());
        return MemberServiceUtils.findMemberByUidAndProvider(memberRepository, response.getId(), provider).getId();
    }

    @Override
    public Long signUp(SignUpRequest request) {
        KaKaoProfileResponse response = kaKaoApiCaller.getProfileInfo(request.getToken());
        return memberService.createMember(request.toCreateMemberRequest(response.getId(), response.getEmail(), provider));
    }

}

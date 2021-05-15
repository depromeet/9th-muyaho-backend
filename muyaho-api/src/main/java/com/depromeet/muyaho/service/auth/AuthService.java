package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.exception.ErrorCode;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.exception.ValidationException;
import com.depromeet.muyaho.external.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.depromeet.muyaho.external.kakao.KaKaoApiCaller;
import com.depromeet.muyaho.external.kakao.dto.response.KaKaoUserInfoResponse;
import com.depromeet.muyaho.service.auth.dto.request.AuthRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignupMemberRequest;
import com.depromeet.muyaho.service.member.MemberService;
import com.depromeet.muyaho.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AppleTokenDecoder appleTokenDecoder;
    private final KaKaoApiCaller kaKaoApiCaller;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Long handleAuthentication(AuthRequest request) {
        if (request.isAppleType()) {
            IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
            return MemberServiceUtils.findMemberByUidAndProvider(memberRepository, payload.getSub(), request.getProvider()).getId();
        }
        if (request.isKaKaoType()) {
            KaKaoUserInfoResponse response = kaKaoApiCaller.getKaKaoUserProfileInfo(request.getToken());
            return MemberServiceUtils.findMemberByUidAndProvider(memberRepository, response.getId(), request.getProvider()).getId();
        }
        throw new ValidationException(String.format("지원하지 않는 소셜 타입 (%s) 입니다.", request.getProvider()), ErrorCode.VALIDATION_NOT_SUPPORTED_PROVIDER_EXCEPTION);
    }

    public Long signUpMember(SignupMemberRequest request) {
        if (request.isAppleType()) {
            IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
            return memberService.createMember(request.toCreateMemberRequest(payload.getSub(), payload.getEmail()));
        }
        if (request.isKaKaoType()) {
            KaKaoUserInfoResponse response = kaKaoApiCaller.getKaKaoUserProfileInfo(request.getToken());
            return memberService.createMember(request.toCreateMemberRequest(response.getId(), response.getEmail()));
        }
        throw new ValidationException(String.format("지원하지 않는 소셜 타입 (%s) 입니다.", request.getProvider()), ErrorCode.VALIDATION_NOT_SUPPORTED_PROVIDER_EXCEPTION);
    }

}

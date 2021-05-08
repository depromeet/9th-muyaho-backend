package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.exception.ConflictException;
import com.depromeet.muyaho.exception.ErrorCode;
import com.depromeet.muyaho.exception.NotFoundException;
import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.exception.ValidationException;
import com.depromeet.muyaho.external.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.depromeet.muyaho.external.kakao.KaKaoApiCaller;
import com.depromeet.muyaho.external.kakao.dto.response.KaKaoUserInfoResponse;
import com.depromeet.muyaho.service.auth.dto.request.AuthRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignupMemberRequest;
import com.depromeet.muyaho.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AppleTokenDecoder appleTokenDecoder;
    private final KaKaoApiCaller kaKaoApiCaller;
    private final MemberRepository memberRepository;

    public Long handleAuthentication(AuthRequest request) {
        if (request.isAppleType()) {
            IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
            return handleAuthentication(payload.getSub(), request.getProvider());
        }
        if (request.isKaKaoType()) {
            KaKaoUserInfoResponse response = kaKaoApiCaller.getKaKaoUserProfileInfo(request.getToken());
            return handleAuthentication(response.getId(), request.getProvider());
        }
        throw new ValidationException(String.format("지원하지 않는 소셜 타입 (%s) 입니다.", request.getProvider()), ErrorCode.VALIDATION_NOT_SUPPORTED_PROVIDER_EXCEPTION);
    }

    private Long handleAuthentication(String uid, MemberProvider provider) {
        Member member = memberRepository.findMemberByUidAndProvider(uid, provider);
        if (member == null) {
            throw new NotFoundException(String.format("존재하지 않는 회원 uid: (%s - %s) 입니다. 회원가입이 필요합니다", provider, uid));
        }
        return member.getId();
    }

    public Long signUpMember(SignupMemberRequest request) {
        MemberServiceUtils.validateNotExistName(memberRepository, request.getName());
        if (request.isAppleType()) {
            IdTokenPayload payload = appleTokenDecoder.getUserInfoFromToken(request.getToken());
            return createNewMember(payload.getSub(), payload.getEmail(), request.getName(), request.getProfileUrl(), request.getProvider());
        }
        if (request.isKaKaoType()) {
            KaKaoUserInfoResponse response = kaKaoApiCaller.getKaKaoUserProfileInfo(request.getToken());
            return createNewMember(response.getId(), response.getEmail(), request.getName(), request.getProfileUrl(), request.getProvider());
        }
        throw new ValidationException(String.format("지원하지 않는 소셜 타입 (%s) 입니다.", request.getProvider()), ErrorCode.VALIDATION_NOT_SUPPORTED_PROVIDER_EXCEPTION);
    }

    private Long createNewMember(String uid, String email, String name, String profileUrl, MemberProvider provider) {
        Member member = memberRepository.findMemberByUidAndProvider(uid, provider);
        if (member != null) {
            throw new ConflictException(String.format("이미 존재하는 멤버 (%s - %s) 입니다", uid, provider));
        }
        Member newMember = memberRepository.save(Member.newInstance(uid, email, name, profileUrl, provider));
        return newMember.getId();
    }

    @Transactional(readOnly = true)
    public void checkNotExistNickName(String name) {
        MemberServiceUtils.validateNotExistName(memberRepository, name);
    }

}

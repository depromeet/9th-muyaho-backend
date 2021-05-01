package com.depromeet.muyaho.controller;

import com.depromeet.muyaho.config.session.MemberSession;
import com.depromeet.muyaho.config.session.SessionConstants;
import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private static final Member testMember = Member.newInstance("test-uid", null, "테스트 계정", null, MemberProvider.KAKAO);

    @Operation(summary = "Health Check")
    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.success("pong");
    }

    // 차후 연동 후 삭제
    @Operation(summary = "(테스트용) 세션을 받아오는 API (Deprecated)", description = "차후 연동 후 사라질 예정")
    @Profile({"local", "dev"})
    @GetMapping("/test-session")
    public ApiResponse<String> testSession() {
        Member findMember = memberRepository.findMemberByUidAndProvider(testMember.getUid(), MemberProvider.KAKAO);
        if (findMember == null) {
            findMember = memberRepository.save(testMember);
        }
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(findMember.getId()));
        return ApiResponse.success(httpSession.getId());
    }

}

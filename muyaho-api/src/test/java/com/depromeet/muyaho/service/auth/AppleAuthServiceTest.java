package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.exception.NotFoundException;
import com.depromeet.muyaho.external.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.depromeet.muyaho.service.auth.dto.request.LoginRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignUpRequest;
import com.depromeet.muyaho.service.member.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.depromeet.muyaho.service.member.MemberServiceTestUtils.assertMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AppleAuthServiceTest {

    private static final String uid = "uid";
    private static final String email = "muyaho@gmail.com";

    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        authService = new AppleAuthService(new StubAppleTokenDecoder(), memberRepository, memberService);
    }

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 애플_로그인_요청시_회원가입한_유저면_멤버의_PK_가_반환된다() {
        // given
        Member member = MemberCreator.create(uid, "무야호", null, MemberProvider.APPLE);
        memberRepository.save(member);

        LoginRequest request = LoginRequest.testInstance("token");

        // when
        Long memberId = authService.login(request);

        // then
        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    void 애플_로그인_요청시_회원가입_하지_않은_유저면_404_에러가_발생한다() {
        // given
        LoginRequest request = LoginRequest.testInstance("token");

        // when & then
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 새로운_유저가_애플로_회원가입요청하면_멤버정보가_DB에_저장된다() {
        // given
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;

        SignUpRequest request = SignUpRequest.testBuilder()
            .token("token")
            .name(name)
            .profileUrl(profileUrl)
            .build();

        authService.signUp(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertMember(memberList.get(0), uid, email, name, profileUrl, provider);
    }

    private static class StubAppleTokenDecoder implements AppleTokenDecoder {
        @Override
        public IdTokenPayload getUserInfoFromToken(String idToken) {
            return IdTokenPayload.testInstance(uid, email);
        }
    }

}

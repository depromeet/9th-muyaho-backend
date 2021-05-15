package com.depromeet.muyaho.service.auth;

import com.depromeet.muyaho.exception.NotFoundException;
import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.external.apple.AppleTokenDecoder;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.depromeet.muyaho.external.kakao.KaKaoApiCaller;
import com.depromeet.muyaho.external.kakao.dto.response.KaKaoUserInfoResponse;
import com.depromeet.muyaho.service.auth.dto.request.AuthRequest;
import com.depromeet.muyaho.service.auth.dto.request.SignupMemberRequest;
import com.depromeet.muyaho.service.member.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AuthServiceTest {

    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    private static final String uid = "uid";
    private static final String email = "muyaho@gmail.com";

    @BeforeEach
    void setUp() {
        authService = new AuthService(new StubAppleTokenDecoder(), new StubKaKaoApiCaller(), memberRepository, memberService);
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

        AuthRequest request = AuthRequest.testInstance("token", MemberProvider.APPLE);

        // when
        Long memberId = authService.handleAuthentication(request);

        // then
        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    void 애플_로그인_요청시_회원가입_하지_않은_유저면_404_에러가_발생한다() {
        // given
        AuthRequest request = AuthRequest.testInstance("token", MemberProvider.APPLE);

        // when & then
        assertThatThrownBy(() -> authService.handleAuthentication(request)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 카카오_로그인_요청시_회원가입한_유저면_멤버의_PK_가_반환된다() {
        // given
        Member member = MemberCreator.create(uid, "무야호", null, MemberProvider.KAKAO);
        memberRepository.save(member);

        AuthRequest request = AuthRequest.testInstance("token", MemberProvider.KAKAO);

        // when
        Long memberId = authService.handleAuthentication(request);

        // then
        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    void 카카오_로그인_요청시_아직_회원가입하지_않은_유저면_404_에러가_발생한다() {
        // given
        AuthRequest request = AuthRequest.testInstance("token", MemberProvider.KAKAO);

        // when & then
        assertThatThrownBy(() -> authService.handleAuthentication(request)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 새로운_유저가_애플로_회원가입요청하면_멤버정보가_DB에_저장된다() {
        // given
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;

        SignupMemberRequest request = SignupMemberRequest.testBuilder()
            .token("token")
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();

        authService.signUpMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertMember(memberList.get(0), uid, email, name, profileUrl, provider);
    }

    @Test
    void 새로운_유저가_카카오로_회원가입시_회원정보가_DB_에_저장된다() {
        // given
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.KAKAO;

        SignupMemberRequest request = SignupMemberRequest.testBuilder()
            .token("token")
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();

        authService.signUpMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertMember(memberList.get(0), uid, email, name, profileUrl, provider);
    }

    private void assertMember(Member member, String uid, String email, String name, String profileUrl, MemberProvider provider) {
        assertThat(member.getUid()).isEqualTo(uid);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(member.getProvider()).isEqualTo(provider);
    }

    private static class StubAppleTokenDecoder implements AppleTokenDecoder {
        @Override
        public IdTokenPayload getUserInfoFromToken(String idToken) {
            return IdTokenPayload.testInstance(uid, email);
        }
    }

    private static class StubKaKaoApiCaller implements KaKaoApiCaller {
        @Override
        public KaKaoUserInfoResponse getKaKaoUserProfileInfo(String accessToken) {
            return KaKaoUserInfoResponse.testInstance(uid, email);
        }
    }

}

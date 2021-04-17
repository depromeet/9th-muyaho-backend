package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.service.member.dto.request.SignUpMemberRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 새로운_멤버가_회원가입하면_멤버_정보가_저장된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";
        String profileUrl = "http://profile.com";
        MemberProvider provider = MemberProvider.KAKAO;

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertMember(memberList.get(0), email, name, profileUrl, provider);
    }

    @Test
    void 회원가입시_같은_프로바이더에_이미_존재하는_이메일일_경우_에러발생() {
        // given
        String email = "will.seungho@gmail.com";
        MemberProvider provider = MemberProvider.KAKAO;
        memberRepository.save(MemberCreator.create(email, provider));

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name("강승호")
            .provider(provider)
            .build();

        // when & then
        assertThatThrownBy(() -> memberService.signUpMember(request)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원가입시_다른_프로바이더에_이미_존재하는_이메일일_경우_정상적으로_가입된다() {
        // given
        String email = "will.seungho@gmail.com";
        MemberProvider provider = MemberProvider.KAKAO;
        memberRepository.save(MemberCreator.create(email, MemberProvider.APPLE));

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name("강승호")
            .provider(provider)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(2);
        assertMember(memberList.get(0), email, MemberProvider.APPLE);
        assertMember(memberList.get(1), email, MemberProvider.KAKAO);
    }

    private void assertMember(Member member, String email, MemberProvider provider) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getProvider()).isEqualTo(provider);
    }

    private void assertMember(Member member, String email, String name, String profileUrl, MemberProvider provider) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(member.getProvider()).isEqualTo(provider);
    }

}

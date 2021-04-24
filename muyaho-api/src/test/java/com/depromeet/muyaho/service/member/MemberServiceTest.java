package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.muyaho.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.service.member.dto.response.MemberInfoResponse;
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

    @Test
    void 멤버_id_를_통해_회원의_정보를_조회한다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";
        String profileUrl = "http://profile.com";
        Member member = memberRepository.save(MemberCreator.create(email, name, profileUrl, MemberProvider.APPLE));

        // when
        MemberInfoResponse response = memberService.getMemberInfo(member.getId());

        // then
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getProfileUrl()).isEqualTo(profileUrl);
    }

    @Test
    void 존재하지_않는_멤버정보를_조회하면_에러가_발생한다() {
        // when & then
        assertThatThrownBy(() -> memberService.getMemberInfo(999L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원_정보를_수정한다() {
        // given
        String email = "will.seungho@gmail.com";
        Member member = MemberCreator.create(email, "강승호", null, MemberProvider.KAKAO);
        memberRepository.save(member);

        String name = "승호강";
        String profileUrl = "http://seungho.com";

        UpdateMemberRequest request = UpdateMemberRequest.testInstance(name, profileUrl);

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<Member> memberList = memberRepository.findAll();
        assertMember(memberList.get(0), email, name, profileUrl, MemberProvider.KAKAO);
    }

    @Test
    void 회원_정보_수정시_존재하지_않는_멤버일경우_에러가_발생한다() {
        // given
        UpdateMemberRequest request = UpdateMemberRequest.testInstance("승호", "profileUrl");

        // when & then
        assertThatThrownBy(() -> memberService.updateMemberInfo(request, 999L)).isInstanceOf(IllegalArgumentException.class);
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

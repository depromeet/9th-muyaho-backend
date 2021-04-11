package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
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

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .build();

        // when
        memberService.signUpMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertMember(memberList.get(0), email, name, profileUrl);
    }

    @Test
    void 회원가입시_이미_존재하는_이메일일_경우_에러발생() {
        // given
        String email = "will.seungho@gmail.com";
        memberRepository.save(MemberCreator.create(email));

        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name("강승호")
            .build();

        // when & then
        assertThatThrownBy(() -> memberService.signUpMember(request)).isInstanceOf(IllegalArgumentException.class);
    }

    private void assertMember(Member member, String email, String name, String profileUrl) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
    }

}

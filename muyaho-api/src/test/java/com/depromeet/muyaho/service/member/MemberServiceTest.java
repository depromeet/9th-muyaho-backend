package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.exception.NotFoundException;
import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
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
    void 회원의_정보를_조회한다() {
        // given
        String name = "강승호";
        String profileUrl = "http://profile.com";
        Member member = memberRepository.save(MemberCreator.create("uid", name, profileUrl, MemberProvider.APPLE));

        // when
        MemberInfoResponse response = memberService.getMemberInfo(member.getId());

        // then
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getProfileUrl()).isEqualTo(profileUrl);
    }

    @Test
    void 존재하지_않는_멤버정보를_조회하면_에러가_발생한다() {
        // when & then
        assertThatThrownBy(() -> memberService.getMemberInfo(999L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 회원_정보를_수정한다() {
        // given
        String uid = "uid";
        Member member = MemberCreator.create(uid, "강승호", null, MemberProvider.KAKAO);
        memberRepository.save(member);

        String name = "승호강";
        String profileUrl = "http://seungho.com";

        UpdateMemberRequest request = UpdateMemberRequest.testInstance(name, profileUrl);

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<Member> memberList = memberRepository.findAll();
        assertMember(memberList.get(0), uid, name, profileUrl, MemberProvider.KAKAO);
    }

    @Test
    void 회원_정보_수정시_존재하지_않는_멤버일경우_에러가_발생한다() {
        // given
        UpdateMemberRequest request = UpdateMemberRequest.testInstance("승호", "profileUrl");

        // when & then
        assertThatThrownBy(() -> memberService.updateMemberInfo(request, 999L)).isInstanceOf(NotFoundException.class);
    }

    private void assertMember(Member member, String uid, String name, String profileUrl, MemberProvider provider) {
        assertThat(member.getUid()).isEqualTo(uid);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(member.getProvider()).isEqualTo(provider);
    }

}

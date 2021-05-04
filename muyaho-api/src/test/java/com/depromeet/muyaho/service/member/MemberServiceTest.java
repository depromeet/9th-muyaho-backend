package com.depromeet.muyaho.service.member;

import com.depromeet.muyaho.domain.member.*;
import com.depromeet.muyaho.exception.NotFoundException;
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

    @Autowired
    private DeleteMemberRepository deleteMemberRepository;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
        deleteMemberRepository.deleteAll();
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

    @Test
    void 회원탈퇴시_MEMBER_테이블에서_해당_데이터가_삭제된다() {
        // given
        Member member = MemberCreator.create("uid", "강승호", null, MemberProvider.KAKAO);
        memberRepository.save(member);

        // when
        memberService.deleteMemberInfo(member.getId());

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).isEmpty();
    }

    @Test
    void 회원탈퇴시_백업_테이블에_해당_데이터가_보관된다() {
        // given
        Member member = MemberCreator.create("uid", "강승호", null, MemberProvider.KAKAO);
        memberRepository.save(member);

        // when
        memberService.deleteMemberInfo(member.getId());

        // then
        List<DeleteMember> deleteMembers = deleteMemberRepository.findAll();
        assertThat(deleteMembers).hasSize(1);
        assertDeleteMember(deleteMembers.get(0), member.getId(), member.getUid(), member.getName(), member.getProfileUrl(), member.getProvider());
    }

    @Test
    void 존재하지_않는_유저를_삭제할_수없다() {
        // when & then
        assertThatThrownBy(() ->  memberService.deleteMemberInfo(999L)).isInstanceOf(NotFoundException.class);
    }

    private void assertDeleteMember(DeleteMember deleteMember, Long id, String uid, String name, String profileUrl, MemberProvider provider) {
        assertThat(deleteMember.getPreviousId()).isEqualTo(id);
        assertThat(deleteMember.getUid()).isEqualTo(uid);
        assertThat(deleteMember.getName()).isEqualTo(name);
        assertThat(deleteMember.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(deleteMember.getProvider()).isEqualTo(provider);
    }

    private void assertMember(Member member, String uid, String name, String profileUrl, MemberProvider provider) {
        assertThat(member.getUid()).isEqualTo(uid);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(member.getProvider()).isEqualTo(provider);
    }

}

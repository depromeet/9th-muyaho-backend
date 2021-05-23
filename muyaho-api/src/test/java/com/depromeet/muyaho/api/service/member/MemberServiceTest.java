package com.depromeet.muyaho.api.service.member;

import com.depromeet.muyaho.api.service.member.dto.request.CheckDuplicateNameRequest;
import com.depromeet.muyaho.api.service.member.dto.request.CreateMemberRequest;
import com.depromeet.muyaho.api.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.api.service.member.dto.response.MemberInfoResponse;
import com.depromeet.muyaho.domain.domain.member.*;
import com.depromeet.muyaho.common.exception.ConflictException;
import com.depromeet.muyaho.common.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
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
    void 회원가입_요청시_DB_에_회원정보가_추가된다() {
        // given
        String uid = "uid";
        String email = "will.seungho@gmail.com";
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;

        CreateMemberRequest request = CreateMemberRequest.builder()
            .uid(uid)
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();

        memberService.createMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        MemberServiceTestUtils.assertMember(memberList.get(0), uid, email, name, profileUrl, provider);
    }

    @Test
    void 회원가입시_이미_존재하는_애플_멤버의경우_409_에러가_발생한다() {
        // given
        String uid = "uid";
        String email = "will.seungho@gmail.com";
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;

        memberRepository.save(MemberCreator.create(uid, "name1", profileUrl, provider));

        CreateMemberRequest request = CreateMemberRequest.builder()
            .uid(uid)
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();

        // when & then
        assertThatThrownBy(() -> memberService.createMember(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원가입시_닉네임이_중복되면_409_에러가_발생한다() {
        // given
        String uid = "uid";
        String email = "will.seungho@gmail.com";
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;

        memberRepository.save(MemberCreator.create("another uuid", name, profileUrl, provider));

        CreateMemberRequest request = CreateMemberRequest.builder()
            .uid(uid)
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .provider(provider)
            .build();

        // when & then
        assertThatThrownBy(() -> memberService.createMember(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 닉네임_중복체크_존재하지_않은_닉네임일경우_통과한다() {
        // given
        CheckDuplicateNameRequest request = CheckDuplicateNameRequest.testInstance("무야호");

        // when
        memberService.checkDuplicateName(request);
    }

    @Test
    void 닉네임_중복체크_이미_존재하는_닉네임일_경우_409_에러가_발생한다() {
        // given
        String name = "무야호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;

        memberRepository.save(MemberCreator.create("another uuid", name, profileUrl, provider));

        CheckDuplicateNameRequest request = CheckDuplicateNameRequest.testInstance(name);

        // when & then
        assertThatThrownBy(() -> memberService.checkDuplicateName(request)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원정보를_요청하면_회원에_대한_정보가_반환된다() {
        // given
        String name = "강승호";
        String profileUrl = "https://profile.com";
        MemberProvider provider = MemberProvider.APPLE;
        Member member = memberRepository.save(MemberCreator.create("uid", name, profileUrl, provider));

        // when
        MemberInfoResponse response = memberService.getMemberInfo(member.getId());

        // then
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(response.getProvider()).isEqualTo(provider);
    }

    @Test
    void 존재하지_않는_멤버정보를_조회하면_404_에러가_발생한다() {
        // when & then
        assertThatThrownBy(() -> memberService.getMemberInfo(999L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 회원_정보를_수정하면_DB_에_수정된_회원정보가_저장된다() {
        // given
        String uid = "uid";
        Member member = MemberCreator.create(uid, "강승호", null, MemberProvider.KAKAO);
        memberRepository.save(member);

        String name = "승호강";
        String profileUrl = "https://seungho.com";

        UpdateMemberRequest request = UpdateMemberRequest.testInstance(name, profileUrl);

        // when
        memberService.updateMemberInfo(request, member.getId());

        // then
        List<Member> memberList = memberRepository.findAll();
        MemberServiceTestUtils.assertMember(memberList.get(0), uid, null, name, profileUrl, MemberProvider.KAKAO);
    }

    @Test
    void 회원정보_수정시_닉네임이_중복되면_409_에러가_발생한다() {
        // given
        String name = "강승호";
        Member member1 = MemberCreator.create("uid1", "승호강", null, MemberProvider.KAKAO);
        Member member2 = MemberCreator.create("uid2", name, null, MemberProvider.KAKAO);
        memberRepository.saveAll(Arrays.asList(member1, member2));

        UpdateMemberRequest request = UpdateMemberRequest.testInstance(name, null);

        // when & then
        assertThatThrownBy(() -> memberService.updateMemberInfo(request, member1.getId())).isInstanceOf(ConflictException.class);
    }

    @Test
    void 회원_정보_수정시_존재하지_않는_멤버일경우_404_에러가_발생한다() {
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
    void 회원탈퇴시_백업_테이블에_해당_데이터가_백업된다() {
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
    void 존재하지_않은_멤버를_삭제요청하면_404_에러가_발생한다() {
        // when & then
        assertThatThrownBy(() -> memberService.deleteMemberInfo(999L)).isInstanceOf(NotFoundException.class);
    }

    private void assertDeleteMember(DeleteMember deleteMember, Long id, String uid, String name, String profileUrl, MemberProvider provider) {
        assertThat(deleteMember.getPreviousId()).isEqualTo(id);
        assertThat(deleteMember.getUid()).isEqualTo(uid);
        assertThat(deleteMember.getName()).isEqualTo(name);
        assertThat(deleteMember.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(deleteMember.getProvider()).isEqualTo(provider);
    }

}

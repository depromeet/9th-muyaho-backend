package com.depromeet.muyaho.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void test_findMemberByEmailAndProvider() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";
        MemberProvider provider = MemberProvider.APPLE;
        memberRepository.save(MemberCreator.create(email, name, null, provider));

        // when
        Member findMember = memberRepository.findMemberByEmailAndProvider(email, provider);

        // then
        assertThat(findMember.getEmail()).isEqualTo(email);
        assertThat(findMember.getName()).isEqualTo(name);
        assertThat(findMember.getProvider()).isEqualTo(MemberProvider.APPLE);
    }

    @Test
    void test_findMemberByEmailAndProvider_프로바이더가_다른경우_조회되지_않는다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";
        MemberProvider provider = MemberProvider.APPLE;
        memberRepository.save(MemberCreator.create(email, name, null, MemberProvider.KAKAO));

        // when
        Member findMember = memberRepository.findMemberByEmailAndProvider(email, provider);

        // then
        assertThat(findMember).isNull();
    }

}

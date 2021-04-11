package com.depromeet.muyaho.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void queryDsl_연동_확인() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";
        memberRepository.save(MemberCreator.create(email, name, null));

        // when
        Member findMember = memberRepository.findMemberByEmail(email);

        // then
        assertThat(findMember.getEmail()).isEqualTo(email);
        assertThat(findMember.getName()).isEqualTo(name);
    }

}

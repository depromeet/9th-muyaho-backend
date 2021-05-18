package com.depromeet.muyaho.api.service;

import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class MemberSetupTest {

    @Autowired
    protected MemberRepository memberRepository;

    protected Long memberId;

    @BeforeEach
    void setupMember() {
        Member member = MemberCreator.create("uid", "muyaho", null, MemberProvider.KAKAO);
        memberRepository.save(member);
        memberId = member.getId();
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}

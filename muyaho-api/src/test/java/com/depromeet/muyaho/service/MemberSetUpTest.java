package com.depromeet.muyaho.service;

import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MemberSetUpTest {

    @Autowired
    protected MemberRepository memberRepository;

    protected Long memberId;

    @BeforeEach
    void setup() {
        Member member = memberRepository.save(MemberCreator.create("will.seungho@gmail.com", MemberProvider.APPLE));
        memberId = member.getId();
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}

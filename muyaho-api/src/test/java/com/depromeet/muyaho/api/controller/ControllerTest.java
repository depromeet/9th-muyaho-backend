package com.depromeet.muyaho.api.controller;

import com.depromeet.muyaho.api.controller.member.api.MemberMockApiCaller;
import com.depromeet.muyaho.domain.domain.member.Member;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.domain.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    protected MemberMockApiCaller memberMockApiCaller;

    protected Member testMember;

    protected String token;

    protected void setup() throws Exception {
        memberMockApiCaller = new MemberMockApiCaller(mockMvc, objectMapper);
        token = memberMockApiCaller.getTestSession().getData();
        testMember = memberRepository.findMemberByUidAndProvider("test-uid", MemberProvider.KAKAO);
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}


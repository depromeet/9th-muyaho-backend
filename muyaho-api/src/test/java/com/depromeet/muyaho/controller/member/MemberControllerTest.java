package com.depromeet.muyaho.controller.member;

import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.controller.ControllerTestUtils;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.muyaho.service.member.dto.response.MemberInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTest extends ControllerTestUtils {

    @Autowired
    protected MemberRepository memberRepository;

    protected MemberMockApiCaller memberMockApiCaller;

    @BeforeEach
    void setUp() {
        memberMockApiCaller = new MemberMockApiCaller(mockMvc, objectMapper);
    }

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 회원가입시_세션이_생성되서_반환된다() throws Exception {
        // given
        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email("will.seungho@gmail.com")
            .name("강승호")
            .provider(MemberProvider.APPLE)
            .build();

        // when
        ApiResponse<String> response = memberMockApiCaller.signUpMember(request, 200);

        // then
        assertThat(response.getCode()).isEqualTo("SUCCESS");
        assertThat(response.getData()).isNotNull();
    }

    @Test
    void 헤더에_포함된_정보를_통해_로그인한_유저의_회원_정보를_조회한다() throws Exception {
        // given
        String email = "ksh980212@gmail.com";
        String name = "강승호";
        String profileUrl = "http://profile.com";

        String sessionId = memberMockApiCaller.getMockMemberSession(email, name, profileUrl);

        // when
        ApiResponse<MemberInfoResponse> response = memberMockApiCaller.getMyMemberInfo(sessionId, 200);

        // then
        assertThat(response.getData().getEmail()).isEqualTo(email);
        assertThat(response.getData().getName()).isEqualTo(name);
        assertThat(response.getData().getProfileUrl()).isEqualTo(profileUrl);
        assertThat(response.getData().getMemberId()).isNotNull();
    }

}

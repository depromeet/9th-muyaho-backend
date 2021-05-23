package com.depromeet.muyaho.api.controller.member;

import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.controller.ControllerTest;
import com.depromeet.muyaho.api.service.member.dto.request.CheckDuplicateNameRequest;
import com.depromeet.muyaho.api.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.api.service.member.dto.response.MemberInfoResponse;
import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.domain.domain.member.MemberCreator;
import com.depromeet.muyaho.domain.domain.member.MemberProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTest extends ControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
    }

    @DisplayName("GET /api/v1/member 내정보 조회 200 OK")
    @Test
    void 내_정보를_불러온다() throws Exception {
        // when
        ApiResponse<MemberInfoResponse> response = memberMockApiCaller.getMemberInfo(token, 200);

        // then
        assertMemberInfoResponse(response.getData(), testMember.getName(), testMember.getProfileUrl(), testMember.getProvider());
    }

    @DisplayName("GET /api/v1/member 잘못된 세션ID이면 401 ERROR")
    @Test
    void 내_정보를_조회시_잘못된_토큰을_경우_401_에러가_발생한다() throws Exception {
        // when
        ApiResponse<MemberInfoResponse> response = memberMockApiCaller.getMemberInfo("Wrong Token", 401);

        // then
        assertThat(response.getCode()).isEqualTo(ErrorCode.UNAUTHORIZED_EXCEPTION.getCode());
    }

    @DisplayName("GET /api/v1/check/name 중복되지 않으면 200 OK")
    @Test
    void 닉네임이_중복되었는지_확인할때_중복되지_않으면_200_OK() throws Exception {
        // given
        CheckDuplicateNameRequest request = CheckDuplicateNameRequest.testInstance("사용하지 않는 닉네임");

        // when
        memberMockApiCaller.checkDuplicateNam(request, 200);
    }

    @DisplayName("GET /api/v1/check/name 중복된 닉네임이면 409 Conflict")
    @Test
    void 닉네임이_중복되었는지_확인할때_이미_존재하면_409() throws Exception {
        // given
        String name = "무야호";
        memberRepository.save(MemberCreator.create("uid", name, null, MemberProvider.KAKAO));

        CheckDuplicateNameRequest request = CheckDuplicateNameRequest.testInstance(name);

        // when
        ApiResponse<String> response = memberMockApiCaller.checkDuplicateNam(request, 409);

        // then
        assertThat(response.getCode()).isEqualTo(ErrorCode.CONFLICT_EXCEPTION.getCode());
    }

    @DisplayName("PUT /api/v1/member 회원 정보 수정 200 OK")
    @Test
    void 멤버정보를_수정하면_변경된_회원정보가_반환된다() throws Exception {
        // given
        String name = "무야호";
        String profileUrl = "https://muyaho.com";
        UpdateMemberRequest request = UpdateMemberRequest.testInstance(name, profileUrl);

        // when
        ApiResponse<MemberInfoResponse> response = memberMockApiCaller.updateMemberInfo(request, token, 200);

        // then
        assertMemberInfoResponse(response.getData(), name, profileUrl, testMember.getProvider());
    }

    private void assertMemberInfoResponse(MemberInfoResponse response, String name, String profileUrl, MemberProvider provider) {
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getProfileUrl()).isEqualTo(profileUrl);
        assertThat(response.getProvider()).isEqualTo(provider);
    }

}

package com.depromeet.muyaho.controller.member;

import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.service.member.dto.request.SignUpMemberRequest;
import com.depromeet.muyaho.service.member.dto.response.MemberInfoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberMockApiCaller {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    public MemberMockApiCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public ApiResponse<String> signUpMember(SignUpMemberRequest request, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = post("/api/v1/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request));

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<MemberInfoResponse> getMyMemberInfo(String sessionId, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v1/member")
            .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(sessionId));
        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public String getMockMemberSession(String email, String name, String profileUrl) throws Exception {
        SignUpMemberRequest request = SignUpMemberRequest.testBuilder()
            .email(email)
            .name(name)
            .profileUrl(profileUrl)
            .provider(MemberProvider.KAKAO)
            .build();
        return signUpMember(request, 200).getData();
    }

}

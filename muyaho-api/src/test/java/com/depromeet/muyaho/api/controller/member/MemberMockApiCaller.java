package com.depromeet.muyaho.api.controller.member;

import com.depromeet.muyaho.api.MockApiCaller;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.service.member.dto.request.CheckDuplicateNameRequest;
import com.depromeet.muyaho.api.service.member.dto.request.UpdateMemberRequest;
import com.depromeet.muyaho.api.service.member.dto.response.MemberInfoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberMockApiCaller extends MockApiCaller {

    public MemberMockApiCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public ApiResponse<String> getTestSession() throws Exception {
        MockHttpServletRequestBuilder builder = get("/test-session");

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<MemberInfoResponse> getMemberInfo(String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v1/member")
            .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token));

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<String> checkDuplicateNam(CheckDuplicateNameRequest request, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v1/check/name")
            .param("name", request.getName());

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

    public ApiResponse<MemberInfoResponse> updateMemberInfo(UpdateMemberRequest request, String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = put("/api/v1/member")
            .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token))
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

    public ApiResponse<String> deleteMemberInfo(String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = delete("/api/v1/member")
            .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token));

        return objectMapper.readValue(
            mockMvc.perform(builder)
                .andExpect(status().is(expectedStatus))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
            }
        );
    }

}

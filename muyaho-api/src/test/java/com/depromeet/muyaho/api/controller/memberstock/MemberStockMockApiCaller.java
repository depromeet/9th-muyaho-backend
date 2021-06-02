package com.depromeet.muyaho.api.controller.memberstock;

import com.depromeet.muyaho.api.controller.MockApiCaller;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.response.MemberStockInfoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberStockMockApiCaller extends MockApiCaller {

    public MemberStockMockApiCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public ApiResponse<MemberStockInfoResponse> addMemberStock(AddMemberStockRequest request, String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = post("/api/v1/member/stock")
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

    public ApiResponse<MemberStockInfoResponse> updateMemberStock(UpdateMemberStockRequest request, String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = put("/api/v1/member/stock")
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

    public ApiResponse<String> deleteMemberStock(DeleteMemberStockRequest request, String token, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = delete("/api/v1/member/stock")
            .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token))
            .param("memberStockId", String.valueOf(request.getMemberStockId()));

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

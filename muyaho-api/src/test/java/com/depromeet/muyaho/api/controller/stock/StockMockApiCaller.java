package com.depromeet.muyaho.api.controller.stock;

import com.depromeet.muyaho.api.controller.MockApiCaller;
import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.service.stock.dto.request.RetrieveStocksRequest;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StockMockApiCaller extends MockApiCaller {

    public StockMockApiCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    public ApiResponse<List<StockInfoResponse>> retrieveStocks(RetrieveStocksRequest request, int expectedStatus) throws Exception {
        MockHttpServletRequestBuilder builder = get("/api/v1/stock/list")
            .param("type", String.valueOf(request.getType()));

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

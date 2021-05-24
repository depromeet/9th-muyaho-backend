package com.depromeet.muyaho.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;

public abstract class MockApiCaller {

    protected final MockMvc mockMvc;

    protected final ObjectMapper objectMapper;

    public MockApiCaller(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

}

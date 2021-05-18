package com.depromeet.muyaho.api.controller;

import com.depromeet.muyaho.api.config.aop.BenchMark;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Operation(summary = "Health Check")
    @BenchMark
    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.success("pong");
    }

}

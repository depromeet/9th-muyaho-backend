package com.depromeet.muyaho.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    public static final ApiResponse<String> SUCCESS = success("SUCCESS");

    private String code;

    private String message;

    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCCESS", "", data);
    }

}

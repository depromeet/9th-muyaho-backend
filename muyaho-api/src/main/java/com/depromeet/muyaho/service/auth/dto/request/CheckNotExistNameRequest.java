package com.depromeet.muyaho.service.auth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckNotExistNameRequest {

    @NotBlank
    private String name;

}

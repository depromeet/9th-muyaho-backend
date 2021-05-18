package com.depromeet.muyaho.domain.service.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckDuplicateNameRequest {

    @NotBlank
    private String name;

    public static CheckDuplicateNameRequest testInstance(String name) {
        return new CheckDuplicateNameRequest(name);
    }

}

package com.depromeet.muyaho.domain.external.slack.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostSlackMessageRequest {

    private String text;

    public static PostSlackMessageRequest of(String text) {
        return new PostSlackMessageRequest(text);
    }

}

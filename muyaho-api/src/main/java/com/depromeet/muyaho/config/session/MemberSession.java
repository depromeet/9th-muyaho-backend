package com.depromeet.muyaho.config.session;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long memberId;

    public static MemberSession of(Long memberId) {
        return new MemberSession(memberId);
    }

}

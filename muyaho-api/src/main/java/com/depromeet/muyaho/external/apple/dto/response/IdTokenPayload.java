package com.depromeet.muyaho.external.apple.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdTokenPayload {

    private String iss;

    private String aud;

    private Long exp;

    private Long iat;

    private String sub;

    private String c_hash;

    private Long auth_time;

    private Boolean nonce_supported;

    private Boolean email_verified;

    private String email;

    private IdTokenPayload(String sub, String email) {
        this.sub = sub;
        this.email = email;
    }

    public static IdTokenPayload testInstance(String uid, String email) {
        return new IdTokenPayload(uid, email);
    }

}

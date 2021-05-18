package com.depromeet.muyaho.domain.external.auth.kakao.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KaKaoUserInfoResponse {

    private String id;
    private KaKaoAccountResponse kakaoAccount;

    public KaKaoUserInfoResponse(String id, KaKaoAccountResponse kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }

    public static KaKaoUserInfoResponse testInstance(String id, String email) {
        return new KaKaoUserInfoResponse(id, new KaKaoAccountResponse(email));
    }

    public String getEmail() {
        return this.kakaoAccount.getEmail();
    }

    @ToString
    @Getter
    @NoArgsConstructor
    private static class KaKaoAccountResponse {

        private String email;

        public KaKaoAccountResponse(String email) {
            this.email = email;
        }

    }

}

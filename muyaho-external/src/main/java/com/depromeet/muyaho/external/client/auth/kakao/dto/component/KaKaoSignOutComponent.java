package com.depromeet.muyaho.external.client.auth.kakao.dto.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kakao.signout")
public class KaKaoSignOutComponent {

    private static final String TOKEN_TYPE = "KakaoAK ";

    private String url;

    private String key;

    public String getAuthorizationValue() {
        return TOKEN_TYPE.concat(key);
    }

}

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
@ConfigurationProperties(prefix = "kakao.profile")
public class KaKaoProfileComponent {

    private String url;

}

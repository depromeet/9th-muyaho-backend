package com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "bitcoin.upbit.markets")
public class UpBitCodesComponent {

    private String url;

}

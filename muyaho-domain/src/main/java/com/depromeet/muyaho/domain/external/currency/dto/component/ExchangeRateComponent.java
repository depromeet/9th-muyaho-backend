package com.depromeet.muyaho.domain.external.currency.dto.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "currency.exchange")
public class ExchangeRateComponent {

    private String url;

}

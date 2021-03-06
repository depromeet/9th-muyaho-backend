package com.depromeet.muyaho.external.client.stock.dto.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "stock.markets")
public class StockCodesComponent {

    private String url;

}

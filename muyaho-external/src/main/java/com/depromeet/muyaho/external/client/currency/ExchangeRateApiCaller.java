package com.depromeet.muyaho.external.client.currency;

import java.math.BigDecimal;

public interface ExchangeRateApiCaller {

    BigDecimal fetchExchangeRate();

}

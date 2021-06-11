package com.depromeet.muyaho.domain.external.currency;

import java.math.BigDecimal;

public interface ExchangeRateApiCaller {

    BigDecimal fetchExchangeRate();

}

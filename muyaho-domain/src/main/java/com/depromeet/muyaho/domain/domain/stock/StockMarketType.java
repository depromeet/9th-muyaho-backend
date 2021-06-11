package com.depromeet.muyaho.domain.domain.stock;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public enum StockMarketType {

	DOMESTIC_STOCK(StockType.STOCK, CurrencyType.WON, Collections.emptyList()),
	OVERSEAS_STOCK(StockType.STOCK, CurrencyType.DOLLAR, Collections.emptyList()),
	BITCOIN(StockType.BITCOIN, CurrencyType.WON, Collections.singletonList("KRW-"));

	public boolean isAllowedCode(String code) {
		if (allowedCodes.isEmpty()) {
			return true;
		}
		return allowedCodes.stream()
				.anyMatch(code::startsWith);
	}

	public boolean isStockType() {
		return this.type.equals(StockType.STOCK);
	}

	public boolean isAllowCurrencyType(CurrencyType currencyType) {
		return this.allowedCurrencyType.equals(currencyType);
	}

	public boolean isTradeByDollars() {
		return this.allowedCurrencyType.equals(CurrencyType.DOLLAR);
	}

	private final StockType type;
	private final CurrencyType allowedCurrencyType;
	private final List<String> allowedCodes;

	private enum StockType {
		STOCK,
		BITCOIN
	}

}

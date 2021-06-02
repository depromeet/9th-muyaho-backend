package com.depromeet.muyaho.domain.domain.dailystockamount.repository;

import com.depromeet.muyaho.domain.domain.dailystockamount.DailyStockAmount;

public interface DailyStockAmountRepositoryCustom {

    DailyStockAmount findLastDailyStockAmount(Long memberId);

}

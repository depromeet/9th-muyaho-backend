package com.depromeet.muyaho.domain.domain.dailystockamount;

import com.depromeet.muyaho.domain.domain.dailystockamount.repository.DailyStockAmountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStockAmountRepository extends JpaRepository<DailyStockAmount, Long>, DailyStockAmountRepositoryCustom {

}

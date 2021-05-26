package com.depromeet.muyaho.domain.domain.stockhistory;

import com.depromeet.muyaho.domain.domain.stockhistory.repository.StockHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long>, StockHistoryRepositoryCustom {

}

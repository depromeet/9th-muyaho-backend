package com.depromeet.muyaho.domain.domain.stock;

import com.depromeet.muyaho.domain.domain.stock.repository.StockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long>, StockRepositoryCustom {

}

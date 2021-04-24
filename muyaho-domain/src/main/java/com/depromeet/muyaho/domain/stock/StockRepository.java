package com.depromeet.muyaho.domain.stock;

import com.depromeet.muyaho.domain.stock.repository.StockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long>, StockRepositoryCustom {

}

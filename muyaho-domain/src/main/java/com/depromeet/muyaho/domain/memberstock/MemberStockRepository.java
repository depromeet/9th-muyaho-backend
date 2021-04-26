package com.depromeet.muyaho.domain.memberstock;

import com.depromeet.muyaho.domain.memberstock.repository.MemberStockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStockRepository extends JpaRepository<MemberStock, Long>, MemberStockRepositoryCustom {

}

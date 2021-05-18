package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.memberstock.repository.MemberStockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStockRepository extends JpaRepository<MemberStock, Long>, MemberStockRepositoryCustom {

}

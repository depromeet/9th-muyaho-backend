package com.depromeet.muyaho.domain.domain.memberstock.repository;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;

import java.util.List;

public interface MemberStockRepositoryCustom {

    MemberStock findByMemberIdAndStockId(Long memberId, Long stockId);

    MemberStock findByIdAndMemberId(Long memberStockId, Long memberId);

    List<MemberStock> findAllActiveStocksByMemberIdAndType(Long memberId, StockMarketType type);

}

package com.depromeet.muyaho.domain.memberstock.repository;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.stock.StockMarketType;

import java.util.List;

public interface MemberStockRepositoryCustom {

    MemberStock findByMemberIdAndStockId(Long memberId, Long stockId);

    MemberStock findByIdAndMemberId(Long memberStockId, Long memberId);

    List<MemberStock> findAllStocksByMemberIdAndType(Long memberId, StockMarketType type);

}

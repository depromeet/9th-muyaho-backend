package com.depromeet.muyaho.domain.memberstock.repository;

import com.depromeet.muyaho.domain.memberstock.MemberStock;

import java.util.List;

public interface MemberStockRepositoryCustom {

    MemberStock findStockByMemberIdAndStockId(Long memberId, Long stockId);

    List<MemberStock> findAllStocksByMemberId(Long memberId);

}

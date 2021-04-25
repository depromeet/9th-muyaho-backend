package com.depromeet.muyaho.domain.memberstock.repository;

import com.depromeet.muyaho.domain.memberstock.MemberStock;

public interface MemberStockRepositoryCustom {

    MemberStock findStockByMemberIdAndStockId(Long memberId, Long stockId);

}

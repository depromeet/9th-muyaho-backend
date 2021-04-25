package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MemberStockServiceUtils {

    static void validateNotExistStockInMember(MemberStockRepository memberStockRepository, Long stockId, Long memberId) {
        MemberStock memberStock = memberStockRepository.findStockByMemberIdAndStockId(memberId, stockId);
        if (memberStock != null) {
            throw new IllegalArgumentException(String.format("멤버 (%s)는 해당하는 주식 (%s)을 이미 소유하고 있습니다"));
        }
    }

}

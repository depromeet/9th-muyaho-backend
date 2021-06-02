package com.depromeet.muyaho.domain.event.memberstock;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockDeletedEvent {

    private final Long memberStockId;

    private final Long memberId;

    public static MemberStockDeletedEvent of(Long memberStockId, Long memberId) {
        return new MemberStockDeletedEvent(memberStockId, memberId);
    }

}

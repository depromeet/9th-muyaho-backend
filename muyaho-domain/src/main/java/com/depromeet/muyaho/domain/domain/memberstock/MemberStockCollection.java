package com.depromeet.muyaho.domain.domain.memberstock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockCollection {

    private final List<MemberStock> memberStockList = new ArrayList<>();

    private MemberStockCollection(List<MemberStock> memberStockList) {
        this.memberStockList.addAll(memberStockList);
    }

    public static MemberStockCollection of(List<MemberStock> memberStocks) {
        return new MemberStockCollection(memberStocks);
    }

    public String extractCodesWithDelimiter(String delimiter) {
        return this.memberStockList.stream()
            .map(MemberStock::getStockCode)
            .collect(Collectors.joining(delimiter));
    }

    public boolean isEmpty() {
        return memberStockList.isEmpty();
    }

    public Map<String, MemberStock> newMemberStockMap() {
        return memberStockList.stream()
            .collect(Collectors.toMap(MemberStock::getStockCode, memberStock -> memberStock));
    }

}

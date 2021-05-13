package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitTradeInfoResponse;
import com.depromeet.muyaho.service.memberstock.dto.response.MemberStockCurrentInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberStockRetrieveService {

    private final MemberStockRepository memberStockRepository;
    private final UpBitApiCaller upBitApiCaller;

    @Transactional(readOnly = true)
    public List<MemberStockCurrentInfoResponse> getMyBitCoinStock(Long memberId) {
        List<MemberStock> bitCoinMemberStocks = memberStockRepository.findAllStocksByMemberIdAndType(memberId, StockMarketType.BITCOIN);
        if (bitCoinMemberStocks.isEmpty()) {
            return Collections.emptyList();
        }
        return retrieveCurrentPrice(bitCoinMemberStocks);
    }

    private List<MemberStockCurrentInfoResponse> retrieveCurrentPrice(List<MemberStock> bitCoinMemberStocks) {
        Map<String, MemberStock> memberStockMap = toMemberStockMap(bitCoinMemberStocks);
        List<UpBitTradeInfoResponse> tradeInfoResponses = upBitApiCaller.retrieveTrades(extractCodeInMemberStock(bitCoinMemberStocks));
        return tradeInfoResponses.stream()
            .map(tradeInfoResponse -> MemberStockCurrentInfoResponse.of(memberStockMap.get(tradeInfoResponse.getMarket()), tradeInfoResponse.getTradePrice()))
            .collect(Collectors.toList());
    }

    private Map<String, MemberStock> toMemberStockMap(List<MemberStock> memberStocks) {
        return memberStocks.stream()
            .collect(Collectors.toMap(MemberStock::getStockCode, memberStock -> memberStock));
    }

    private String extractCodeInMemberStock(List<MemberStock> memberStocks) {
        return memberStocks.stream()
            .map(MemberStock::getStockCode)
            .collect(Collectors.joining(","));
    }

}

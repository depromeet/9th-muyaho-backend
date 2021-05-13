package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockCollection;
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
        MemberStockCollection collection = MemberStockCollection.of(memberStockRepository.findAllStocksByMemberIdAndType(memberId, StockMarketType.BITCOIN));
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return retrieveCurrentPrice(collection);
    }

    private List<MemberStockCurrentInfoResponse> retrieveCurrentPrice(MemberStockCollection collection) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<UpBitTradeInfoResponse> tradeInfoResponses = upBitApiCaller.retrieveTrades(collection.extractCodesWithDelimiter(","));
        return tradeInfoResponses.stream()
            .map(tradeInfoResponse -> MemberStockCurrentInfoResponse.of(memberStockMap.get(tradeInfoResponse.getMarket()), tradeInfoResponse.getTradePrice()))
            .collect(Collectors.toList());
    }

}

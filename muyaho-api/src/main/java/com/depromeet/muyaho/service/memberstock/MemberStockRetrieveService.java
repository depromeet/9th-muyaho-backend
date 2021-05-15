package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.external.stock.StockApiCaller;
import com.depromeet.muyaho.external.stock.dto.response.StockPriceResponse;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitTradeInfoResponse;
import com.depromeet.muyaho.service.memberstock.dto.response.MemberStockCurrentInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberStockRetrieveService {

    private static final String DELIMITER = ",";

    private final MemberStockRepository memberStockRepository;
    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;

    public List<MemberStockCurrentInfoResponse> getMemberCurrentStocks(StockMarketType type, Long memberId) {
        MemberStockCollection collection = MemberStockCollection.of(memberStockRepository.findAllStocksByMemberIdAndType(memberId, type));
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return getCurrentInfo(type, collection);
    }

    private List<MemberStockCurrentInfoResponse> getCurrentInfo(StockMarketType type, MemberStockCollection collection) {
        if (type.isStockType()) {
            return getStockCurrentInfo(collection);
        }
        return getBitCoinCurrentInfo(collection);
    }

    private List<MemberStockCurrentInfoResponse> getStockCurrentInfo(MemberStockCollection collection) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<StockPriceResponse> stockPrices = stockApiCaller.getStockPrice(collection.extractCodesWithDelimiter(DELIMITER));
        return stockPrices.stream()
            .map(tradeInfoResponse -> MemberStockCurrentInfoResponse.of(memberStockMap.get(tradeInfoResponse.getCode()), tradeInfoResponse.getPrice()))
            .collect(Collectors.toList());
    }

    private List<MemberStockCurrentInfoResponse> getBitCoinCurrentInfo(MemberStockCollection collection) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<UpBitTradeInfoResponse> tradeInfoResponses = upBitApiCaller.retrieveTrades(collection.extractCodesWithDelimiter(DELIMITER));
        return tradeInfoResponses.stream()
            .map(tradeInfoResponse -> MemberStockCurrentInfoResponse.of(memberStockMap.get(tradeInfoResponse.getMarket()), tradeInfoResponse.getTradePrice()))
            .collect(Collectors.toList());
    }

}

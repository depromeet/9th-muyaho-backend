package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.service.memberstock.dto.response.MemberStockInfoResponse;
import com.depromeet.muyaho.service.stock.StockServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberStockService {

    private final MemberStockRepository memberStockRepository;
    private final StockRepository stockRepository;

    @Transactional
    public MemberStockInfoResponse addMemberStock(AddMemberStockRequest request, Long memberId) {
        MemberStockServiceUtils.validateNotExistStockInMember(memberStockRepository, request.getStockId(), memberId);
        Stock findStock = StockServiceUtils.findStockById(stockRepository, request.getStockId());
        MemberStock memberStock = memberStockRepository.save(request.toEntity(memberId));
        return MemberStockInfoResponse.of(memberStock, findStock);
    }

}

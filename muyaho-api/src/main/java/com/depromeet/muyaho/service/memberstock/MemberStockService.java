package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.DeletedMemberSockRepository;
import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.service.memberstock.dto.request.UpdateMemberStockRequest;
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
    private final DeletedMemberSockRepository deletedMemberSockRepository;

    @Transactional
    public MemberStockInfoResponse addMemberStock(AddMemberStockRequest request, Long memberId) {
        MemberStockServiceUtils.validateNotExistStockInMember(memberStockRepository, request.getStockId(), memberId);
        Stock findStock = StockServiceUtils.findActiveStockById(stockRepository, request.getStockId());
        MemberStock memberStock = memberStockRepository.save(request.toEntity(memberId, findStock));
        return MemberStockInfoResponse.of(memberStock, memberStock.getStock());
    }

    @Transactional
    public MemberStockInfoResponse updateMemberStock(UpdateMemberStockRequest request, Long memberId) {
        MemberStock memberStock = MemberStockServiceUtils.findMemberStockByIdAndMemberId(memberStockRepository, request.getMemberStockId(), memberId);
        memberStock.updateAmount(request.getPurchasePrice(), request.getQuantity());
        return MemberStockInfoResponse.of(memberStock, memberStock.getStock());
    }

    @Transactional
    public void deleteMemberStock(DeleteMemberStockRequest request, Long memberId) {
        MemberStock memberStock = MemberStockServiceUtils.findMemberStockByIdAndMemberId(memberStockRepository, request.getMemberStockId(), memberId);
        deletedMemberSockRepository.save(memberStock.delete());
        memberStockRepository.delete(memberStock);
    }

}

package com.depromeet.muyaho.domain.service.memberstock;

import com.depromeet.muyaho.domain.event.memberstock.MemberStockDeletedEvent;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.response.MemberStockInfoResponse;
import com.depromeet.muyaho.domain.service.stock.StockServiceUtils;
import com.depromeet.muyaho.domain.domain.memberstock.DeletedMemberSockRepository;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberStockService {

    private final ApplicationEventPublisher eventPublisher;
    private final MemberStockRepository memberStockRepository;
    private final StockRepository stockRepository;
    private final DeletedMemberSockRepository deletedMemberSockRepository;

    @Transactional
    public MemberStockInfoResponse addMemberStock(AddMemberStockRequest request, Long memberId) {
        MemberStockServiceUtils.validateNotExistStockInMember(memberStockRepository, request.getStockId(), memberId);
        Stock findStock = StockServiceUtils.findActiveStockById(stockRepository, request.getStockId());
        return MemberStockInfoResponse.of(memberStockRepository.save(request.toEntity(memberId, findStock)), findStock);
    }

    @Transactional
    public MemberStockInfoResponse updateMemberStock(UpdateMemberStockRequest request, Long memberId) {
        MemberStock memberStock = MemberStockServiceUtils.findMemberStockByIdAndMemberId(memberStockRepository, request.getMemberStockId(), memberId);
        memberStock.updateAmount(request.getPurchasePrice(), request.getQuantity(), request.getPurchaseTotalPrice());
        return MemberStockInfoResponse.of(memberStock, memberStock.getStock());
    }

    @Transactional
    public void deleteMemberStock(DeleteMemberStockRequest request, Long memberId) {
        MemberStock memberStock = MemberStockServiceUtils.findMemberStockByIdAndMemberId(memberStockRepository, request.getMemberStockId(), memberId);
        eventPublisher.publishEvent(MemberStockDeletedEvent.of(request.getMemberStockId(), memberId));
        deletedMemberSockRepository.save(memberStock.delete());
        memberStockRepository.delete(memberStock);
    }

}

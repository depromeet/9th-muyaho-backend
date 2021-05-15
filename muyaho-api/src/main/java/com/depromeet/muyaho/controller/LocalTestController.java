package com.depromeet.muyaho.controller;

import com.depromeet.muyaho.config.session.MemberSession;
import com.depromeet.muyaho.config.session.SessionConstants;
import com.depromeet.muyaho.domain.member.Member;
import com.depromeet.muyaho.domain.member.MemberProvider;
import com.depromeet.muyaho.domain.member.MemberRepository;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.external.stock.StockApiCaller;
import com.depromeet.muyaho.external.stock.StockType;
import com.depromeet.muyaho.service.stock.dto.request.StockInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Deprecated 연동되기 전까지 테스트용도의 API
 */
@Deprecated
@Profile({"local", "dev"})
@RequiredArgsConstructor
@RestController
public class LocalTestController {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private static final Member testMember = Member.newInstance("test-uid", null, "테스트 계정", null, MemberProvider.KAKAO);
    private final StockApiCaller stockApiCaller;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/test-session")
    public ApiResponse<String> testSession() {
        Member findMember = memberRepository.findMemberByUidAndProvider(testMember.getUid(), MemberProvider.KAKAO);
        if (findMember == null) {
            findMember = memberRepository.save(testMember);
        }
        httpSession.setAttribute(SessionConstants.AUTH_SESSION, MemberSession.of(findMember.getId()));
        return ApiResponse.success(httpSession.getId());
    }

    @GetMapping("/renew-domestic")
    public String renewDomesticStocks() {
        List<StockInfoRequest> stockInfoList = stockApiCaller.getStockCodes(StockType.DOMESTIC_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.DOMESTIC_STOCK, stockInfoList));
        return "OK";
    }

    @GetMapping("/renew-overseas")
    public String renewOverseaStocks() {
        List<StockInfoRequest> stockInfoList = stockApiCaller.getStockCodes(StockType.OVERSEAS_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.OVERSEAS_STOCK, stockInfoList));
        return "OK";
    }

}

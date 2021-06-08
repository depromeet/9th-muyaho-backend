package com.depromeet.muyaho.batch.job;

import com.depromeet.muyaho.batch.config.UniqueRunIdIncrementer;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.service.stock.StockRenewService;
import com.depromeet.muyaho.domain.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.domain.external.stock.StockApiCaller;
import com.depromeet.muyaho.domain.external.stock.StockType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StockRenewJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;
    private final StockRenewService stockRenewService;

    @Bean
    public Job stockRenewJob() {
        return jobBuilderFactory.get("stockRenewJob")
            .incrementer(new UniqueRunIdIncrementer())
            .start(renewBitCoinStep())
            .next(renewDomesticStockStep())
            .next(renewNasdaqStockStep())
            .next(renewNyseStockStep())
            .build();
    }

    @Bean
    public Step renewBitCoinStep() {
        return stepBuilderFactory.get("renewBitCoinStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("상장된 비트코인 종목 현황을 갱신합니다");

                List<StockInfoRequest> bitCoinStocks = upBitApiCaller.fetchListedBitcoins().stream()
                    .map(market -> StockInfoRequest.of(market.getMarket(), market.getKoreanName()))
                    .collect(Collectors.toList());
                stockRenewService.renewStock(StockMarketType.BITCOIN, bitCoinStocks);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step renewDomesticStockStep() {
        return stepBuilderFactory.get("renewDomesticStockStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("상장된 국내주식 종목 현황을 갱신합니다");

                List<StockInfoRequest> domesticStocks = stockApiCaller.fetchListedStocksCodes(StockType.DOMESTIC_STOCK).stream()
                    .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
                    .collect(Collectors.toList());
                stockRenewService.renewStock(StockMarketType.DOMESTIC_STOCK, domesticStocks);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step renewNasdaqStockStep() {
        return stepBuilderFactory.get("renewOverSeaStockStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("상장된 해외주식 종목 현황을 갱신합니다");

                List<StockInfoRequest> overSeasStocks = stockApiCaller.fetchListedStocksCodes(StockType.NASDAQ).stream()
                    .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
                    .collect(Collectors.toList());
                stockRenewService.renewStock(StockMarketType.OVERSEAS_STOCK, overSeasStocks);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step renewNyseStockStep() {
        return stepBuilderFactory.get("renewOverSeaStockStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("상장된 해외주식 종목 현황을 갱신합니다");

                List<StockInfoRequest> overSeasStocks = stockApiCaller.fetchListedStocksCodes(StockType.NYSE).stream()
                    .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
                    .collect(Collectors.toList());
                stockRenewService.renewStock(StockMarketType.OVERSEAS_STOCK, overSeasStocks);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}

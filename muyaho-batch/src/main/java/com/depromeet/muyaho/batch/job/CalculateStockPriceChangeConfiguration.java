package com.depromeet.muyaho.batch.job;

import com.depromeet.muyaho.batch.config.UniqueRunIdIncrementer;
import com.depromeet.muyaho.common.exception.NotFoundException;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.domain.stockchange.StockPriceChange;
import com.depromeet.muyaho.domain.domain.stockchange.StockPriceChangeRepository;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.response.UpBitPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class CalculateStockPriceChangeConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final UpBitApiCaller upBitApiCaller;
    private final StockRepository stockRepository;
    private final StockPriceChangeRepository stockPriceChangeRepository;

    @Bean
    public Job calculateStockPriceJob() {
        return jobBuilderFactory.get("calculateStockPriceJob")
            .incrementer(new UniqueRunIdIncrementer())
            .start(retrieveTodayBitCoinPriceStep())
            .build();
    }

    @Bean
    public Step retrieveTodayBitCoinPriceStep() {
        return stepBuilderFactory.get("retrieveTodayBitCoinPriceStep")
            .tasklet((contribution, chunkContext) -> {
                List<Stock> stocks = stockRepository.findAllActiveStockByType(StockMarketType.BITCOIN);
                List<UpBitPriceResponse> priceResponses = upBitApiCaller.fetchCurrentBitcoinPrice(getEntireStocksCode(stocks));
                List<StockPriceChange> stockPriceChanges = priceResponses.stream()
                    .map(response -> StockPriceChange.of(findStockById(stocks, response.getMarket()).getId(), response.getTradePrice()))
                    .collect(Collectors.toList());
                stockPriceChangeRepository.saveAll(stockPriceChanges);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    private Stock findStockById(List<Stock> stocks, String stockCode) {
        return stocks.stream()
            .filter(stock -> stock.getCode().equals(stockCode))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(String.format("해당하는 주식 (%s)은 존재하지 않습니다", stockCode)));
    }

    private String getEntireStocksCode(List<Stock> stocks) {
        return stocks.stream()
            .map(Stock::getCode)
            .collect(Collectors.joining(","));
    }

}

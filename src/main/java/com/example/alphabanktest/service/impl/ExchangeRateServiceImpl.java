package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.feign.ExchangeRateClient;
import com.example.alphabanktest.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@AllArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateClient exchangeRateClient;

    @Override
    public Double getLatestCurrencyValueVersusDollar(String currency) {
        return exchangeRateClient.getLatestExchangeRate().getRates().get(currency);
    }

    @Override
    public Double getYesterdayCurrencyValueVersusDollar(String currency) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return exchangeRateClient.getExchangeRateByDate(simpleDateFormat.format(calendar.getTime()))
                .getRates()
                .get(currency);
    }

    @Override
    public boolean isLatestCurrencyValueMoreThanYesterday(String currency) {
        return this.getLatestCurrencyValueVersusDollar(currency) > this.getYesterdayCurrencyValueVersusDollar(currency);
    }
}

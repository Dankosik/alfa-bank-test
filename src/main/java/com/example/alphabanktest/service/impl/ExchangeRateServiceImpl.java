package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.exceptions.CurrencyNotFoundException;
import com.example.alphabanktest.feign.ExchangeRateClient;
import com.example.alphabanktest.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

@Service
@Slf4j
@AllArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateClient exchangeRateClient;

    @Override
    public boolean isLatestCurrencyValueMoreThanYesterday(String currency) {
        return this.getLatestCurrencyValueVersusDollar(currency) > this.getYesterdayCurrencyValueVersusDollar(currency);
    }

    private Double getLatestCurrencyValueVersusDollar(String currency) {
        HashMap<String, Double> rates = exchangeRateClient.getLatestExchangeRate().getRates();
        if (!rates.containsKey(currency)) {
            log.warn("Currency: " + currency + " is not found");
            throw new CurrencyNotFoundException("Currency: " + currency + " is not found");
        }
        return rates.get(currency);
    }

    private Double getYesterdayCurrencyValueVersusDollar(String currency) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        HashMap<String, Double> rates = exchangeRateClient
                .getExchangeRateByDate(simpleDateFormat.format(calendar.getTime())).getRates();

        if (!rates.containsKey(currency)) {
            log.warn("Currency: " + currency + " is not found");
            throw new CurrencyNotFoundException("Currency: " + currency + " is not found");
        }
        return rates.get(currency);
    }
}

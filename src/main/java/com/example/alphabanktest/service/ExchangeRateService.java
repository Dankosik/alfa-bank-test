package com.example.alphabanktest.service;

public interface ExchangeRateService {
    Double getLatestCurrencyValueVersusDollar(String currency);

    Double getYesterdayCurrencyValueVersusDollar(String currency);

    boolean isLatestCurrencyValueMoreThanYesterday(String currency);
}

package com.example.alphabanktest.service;

public interface ExchangeRateService {
    boolean isLatestCurrencyValueMoreThanYesterday(String currency);
}

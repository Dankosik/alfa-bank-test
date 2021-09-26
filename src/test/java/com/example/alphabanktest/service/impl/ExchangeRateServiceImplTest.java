package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.dto.currency.ExchangeRate;
import com.example.alphabanktest.exception.CurrencyNotFoundException;
import com.example.alphabanktest.feignclient.ExchangeRateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ExchangeRateServiceImpl.class})
class ExchangeRateServiceImplTest {
    private final ExchangeRate exchangeRate = new ExchangeRate();
    private final ExchangeRate yesterdayExchangeRate = new ExchangeRate();
    @Autowired
    private ExchangeRateServiceImpl exchangeRateService;
    @MockBean
    private ExchangeRateClient exchangeRateClient;
    private String format;


    @BeforeEach
    void setUp() {
        exchangeRate.setBase("USD");
        HashMap<String, Double> rates = new HashMap<>();
        rates.put("RUB", 75.12);
        rates.put("AGA", 44.55);
        rates.put("JAS", 22.1);
        exchangeRate.setRates(rates);

        yesterdayExchangeRate.setBase("USD");
        HashMap<String, Double> yesterdayRates = new HashMap<>();
        yesterdayRates.put("RUB", 73.15);
        yesterdayRates.put("AGA", 45.55);
        yesterdayRates.put("JAS", 21.1);
        yesterdayExchangeRate.setRates(yesterdayRates);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        format = simpleDateFormat.format(calendar.getTime());
    }

    @Test
    void whenLatestCurrencyValueMoreThanYesterday_thenTrueShouldBeReturned() {
        when(exchangeRateClient.getLatestExchangeRate()).thenReturn(exchangeRate);
        when(exchangeRateClient.getExchangeRateByDate(format)).thenReturn(yesterdayExchangeRate);

        assertTrue(exchangeRateService.isLatestCurrencyValueMoreThanYesterday("RUB"));

        verify(exchangeRateClient, times(1)).getLatestExchangeRate();
        verify(exchangeRateClient, times(1)).getExchangeRateByDate(format);
    }

    @Test
    void whenLatestCurrencyValueLessThanYesterday_thenFalseShouldBeReturned() {
        when(exchangeRateClient.getLatestExchangeRate()).thenReturn(exchangeRate);
        when(exchangeRateClient.getExchangeRateByDate(format)).thenReturn(yesterdayExchangeRate);

        assertFalse(exchangeRateService.isLatestCurrencyValueMoreThanYesterday("AGA"));

        verify(exchangeRateClient, times(1)).getLatestExchangeRate();
        verify(exchangeRateClient, times(1)).getExchangeRateByDate(format);
    }

    @Test
    void whenCurrencyIsNotFoundInLatestExchangeRate_thenCurrencyNotFoundExceptionShouldBeThrown() {
        when(exchangeRateClient.getLatestExchangeRate()).thenReturn(exchangeRate);

        assertThrows(CurrencyNotFoundException.class, () -> exchangeRateService.isLatestCurrencyValueMoreThanYesterday("ADS"));

        verify(exchangeRateClient, times(1)).getLatestExchangeRate();
        verify(exchangeRateClient, never()).getExchangeRateByDate(format);
    }

    @Test
    void whenCurrencyIsNotFoundInYesterdayExchangeRate_thenCurrencyNotFoundExceptionShouldBeThrown() {
        when(exchangeRateClient.getExchangeRateByDate("2021-09-23")).thenReturn(yesterdayExchangeRate);
        when(exchangeRateClient.getLatestExchangeRate()).thenReturn(exchangeRate);

        assertThrows(CurrencyNotFoundException.class, () -> exchangeRateService.isLatestCurrencyValueMoreThanYesterday("ADS"));

        verify(exchangeRateClient, times(1)).getLatestExchangeRate();
        verify(exchangeRateClient, never()).getExchangeRateByDate(format);
    }
}
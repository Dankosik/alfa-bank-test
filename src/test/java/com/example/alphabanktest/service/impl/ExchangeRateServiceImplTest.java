package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.exception.CurrencyNotFoundException;
import com.example.alphabanktest.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
class ExchangeRateServiceImplTest {
    @Value(value = "${exchange-rate-api.api-key}")
    private String API_KEY;
    @Autowired
    private ExchangeRateService exchangeRateService;
    private String format;


    @BeforeEach
    void setUp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        format = simpleDateFormat.format(calendar.getTime());
    }

    @Test
    void whenLatestCurrencyValueMoreThanYesterday_thenTrueShouldBeReturned() {
        stubForLatestExchangeRate();
        stubForYesterdayExchangeRate(format);

        assertTrue(exchangeRateService.isLatestCurrencyValueMoreThanYesterday("RUB"));
    }

    @Test
    void whenLatestCurrencyValueLessThanYesterday_thenFalseShouldBeReturned() {
        stubForLatestExchangeRate();
        stubForYesterdayExchangeRate(format);

        assertFalse(exchangeRateService.isLatestCurrencyValueMoreThanYesterday("AGA"));
    }

    @Test
    void whenCurrencyIsNotFoundInLatestExchangeRate_thenCurrencyNotFoundExceptionShouldBeThrown() {
        stubForLatestExchangeRate();

        assertThrows(CurrencyNotFoundException.class, () -> exchangeRateService.isLatestCurrencyValueMoreThanYesterday("ADS"));
    }

    @Test
    void whenCurrencyIsNotFoundInYesterdayExchangeRate_thenCurrencyNotFoundExceptionShouldBeThrown() {
        stubForLatestExchangeRate();
        stubForYesterdayExchangeRate("2021-09-23");

        assertThrows(CurrencyNotFoundException.class, () -> exchangeRateService.isLatestCurrencyValueMoreThanYesterday("ADS"));
    }

    private void stubForLatestExchangeRate() {
        stubFor(get(urlEqualTo("/latest.json?app_id=" + API_KEY))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;")
                        .withBody("{\"base\":\"USD\", \"rates\":{\"RUB\":\"75.12\",\"AGA\":\"44.55\",\"JAS\":\"22.1\"}}")));
    }

    private void stubForYesterdayExchangeRate(String date) {
        stubFor(get(urlEqualTo("/historical/" + date + ".json?app_id=" + API_KEY))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json;")
                        .withBody("{\"base\":\"USD\", \"rates\":{\"RUB\":\"73.15\",\"AGA\":\"45.55\",\"JAS\":\"21.1\"}}")));
    }
}
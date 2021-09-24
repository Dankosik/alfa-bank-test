package com.example.alphabanktest.feign;

import com.example.alphabanktest.dto.currency.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rate", url = "${exchange-rate-api.url.value}")
public interface ExchangeRateClient {
    @GetMapping("/latest.json?app_id=${exchange-rate-api.api-key}")
    ExchangeRate getLatestExchangeRate();

    @GetMapping("/historical/{date}.json?app_id=${exchange-rate-api.api-key}")
    ExchangeRate getExchangeRateByDate(@PathVariable String date);
}

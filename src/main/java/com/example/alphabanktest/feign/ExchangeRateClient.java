package com.example.alphabanktest.feign;

import com.example.alphabanktest.dto.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rate", url = "https://openexchangerates.org/api")
public interface ExchangeRateClient {
    @GetMapping("/latest.json?app_id=0927b48bb2cb438fa303e43c7eb0a348")
    ExchangeRate getLatestExchangeRate();

    @GetMapping("/historical/{date}.json?app_id=0927b48bb2cb438fa303e43c7eb0a348")
    ExchangeRate getExchangeRateByDate(@PathVariable String date);
}

package com.example.alphabanktest.controller;

import com.example.alphabanktest.dto.gif.GifOriginalWrapper;
import com.example.alphabanktest.service.ExchangeRateService;
import com.example.alphabanktest.service.GifService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/exchange-rate")
@AllArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    private final GifService gifService;

    @GetMapping("/{currency}")
    ResponseEntity<GifOriginalWrapper> getGif(@PathVariable String currency) {
        if (exchangeRateService.isLatestCurrencyValueMoreThanYesterday(currency)) {
            return new ResponseEntity<>(gifService.getRandomGifByName("rich"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(gifService.getRandomGifByName("broke"), HttpStatus.OK);
        }
    }
}

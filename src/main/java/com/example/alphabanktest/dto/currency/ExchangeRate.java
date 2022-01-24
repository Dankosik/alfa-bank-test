package com.example.alphabanktest.dto.currency;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExchangeRate {
    private String base;
    private Map<String, Double> rates;
}

package com.example.alphabanktest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class ExchangeRate {
    private String base;
    private HashMap<String, Double> rates;
}

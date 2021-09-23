package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.dto.gif.GifApiWrapper;
import com.example.alphabanktest.service.GifService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GifServiceImpl implements GifService {
    @Override
    public String getRandomGifUrl(GifApiWrapper gifApiWrapper) {
        int randomIndex = getRandomIndex(gifApiWrapper);
        return gifApiWrapper.getData()[randomIndex].getImages().getOriginal().getUrl();
    }

    private int getRandomIndex(GifApiWrapper gifApiWrapper) {
        Random random = new Random();
        int max = gifApiWrapper.getData().length;
        int min = 0;
        return random.nextInt((max - min) + 1) + min;
    }
}

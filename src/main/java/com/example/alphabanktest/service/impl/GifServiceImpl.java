package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.dto.gif.GifApiOriginalWrapper;
import com.example.alphabanktest.dto.gif.GifApiWrapper;
import com.example.alphabanktest.feign.GifClient;
import com.example.alphabanktest.service.GifService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class GifServiceImpl implements GifService {
    private final GifClient gifClient;

    @Override
    public GifApiOriginalWrapper getRandomGifByName(String name) {
        GifApiWrapper gifApiWrapper = gifClient.getGifApiWrapperByName(name);
        int randomIndex = getRandomIndex(gifApiWrapper);
        return gifApiWrapper.getData()[randomIndex].getImages().getOriginal();
    }

    private int getRandomIndex(GifApiWrapper gifApiWrapper) {
        Random random = new Random();
        int max = gifApiWrapper.getData().length;
        int min = 0;
        return random.nextInt((max - min) + 1) + min;
    }
}

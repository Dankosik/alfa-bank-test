package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.dto.gif.GifApiWrapper;
import com.example.alphabanktest.dto.gif.GifOriginalWrapper;
import com.example.alphabanktest.exceptions.GifNotFoundException;
import com.example.alphabanktest.feign.GifClient;
import com.example.alphabanktest.service.GifService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class GifServiceImpl implements GifService {
    private final GifClient gifClient;

    @Override
    public GifOriginalWrapper getRandomGifByName(String name) {
        if (gifClient.getGifApiWrapperByGifName(name).getData().length == 0) {
            log.warn("Gifs with name: " + name + " are not found");
            throw new GifNotFoundException("Gifs with name: " + name + " are not found");
        }
        GifApiWrapper gifApiWrapper = gifClient.getGifApiWrapperByGifName(name);
        int randomIndex = getRandomIndex(gifApiWrapper);
        return gifApiWrapper.getData()[randomIndex].getImages().getOriginal();
    }

    private int getRandomIndex(GifApiWrapper gifApiWrapper) {
        Random random = new Random();
        int max = gifApiWrapper.getData().length;
        if (max == 1) {
            return 0;
        }
        int min = 0;
        return random.nextInt((max - min) + 1) + min;
    }
}

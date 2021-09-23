package com.example.alphabanktest.service;

import com.example.alphabanktest.dto.gif.GifApiWrapper;

public interface GifService {
    String getRandomGifUrl(GifApiWrapper gifApiWrapper);
}

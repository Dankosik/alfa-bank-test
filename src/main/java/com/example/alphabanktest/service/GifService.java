package com.example.alphabanktest.service;

import com.example.alphabanktest.dto.gif.GifApiOriginalWrapper;

public interface GifService {
    GifApiOriginalWrapper getRandomGifByName(String name);
}

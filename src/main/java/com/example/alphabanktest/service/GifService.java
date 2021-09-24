package com.example.alphabanktest.service;

import com.example.alphabanktest.dto.gif.GifOriginalWrapper;

public interface GifService {
    GifOriginalWrapper getRandomGifByName(String name);
}

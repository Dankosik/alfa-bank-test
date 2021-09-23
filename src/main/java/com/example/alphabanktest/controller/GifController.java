package com.example.alphabanktest.controller;

import com.example.alphabanktest.dto.gif.GifApiOriginalWrapper;
import com.example.alphabanktest.feign.GifClient;
import com.example.alphabanktest.service.GifService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gif")
@AllArgsConstructor
public class GifController {
    private final GifClient gifClient;
    private final GifService gifService;

    @GetMapping("/{key-word}")
    public GifApiOriginalWrapper getGifUrl(@PathVariable("key-word") String keyWord) {
        GifApiOriginalWrapper response = new GifApiOriginalWrapper();
        response.setUrl(gifService.getRandomGifUrl(gifClient.getGifUrlByName(keyWord)));
        return response;
    }
}

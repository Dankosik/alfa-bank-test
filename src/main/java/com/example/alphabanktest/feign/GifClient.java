package com.example.alphabanktest.feign;

import com.example.alphabanktest.dto.gif.GifApiWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy", url = "${gif-api.url.value}")
public interface GifClient {
    @GetMapping("/search?api_key=${gif-api.api-key}" +
            "&limit=${gif-api.url.limit-argument}" +
            "&offset=${gif-api.url.offset-argument}" +
            "&rating=${gif-api.url.rating-argument}" +
            "&lang=${gif-api.url.lang-argument}")
    GifApiWrapper getGifApiWrapperByName(@RequestParam String q);
}

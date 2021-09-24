package com.example.alphabanktest.feign;

import com.example.alphabanktest.dto.gif.GifApiWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy", url = "${gif-api.url}")
public interface GifClient {
    @GetMapping("/search?api_key=${gif-api.api-key}")
    GifApiWrapper getGifApiWrapperByName(@RequestParam String q);
}

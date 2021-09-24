package com.example.alphabanktest.feign;

import com.example.alphabanktest.dto.gif.GifApiWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy", url = "https://api.giphy.com/v1/gifs/search?api_key=MoHieYC44O2SRXtBfEtumvod6oCwER56")
public interface GifClient {
    @GetMapping
    GifApiWrapper getGifApiWrapperByName(@RequestParam String q);
}

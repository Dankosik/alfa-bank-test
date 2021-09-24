package com.example.alphabanktest.controller;

import com.example.alphabanktest.dto.gif.GifApiDataElementWrapper;
import com.example.alphabanktest.dto.gif.GifApiImagesWrapper;
import com.example.alphabanktest.dto.gif.GifApiOriginalWrapper;
import com.example.alphabanktest.dto.gif.GifApiWrapper;
import com.example.alphabanktest.service.ExchangeRateService;
import com.example.alphabanktest.service.GifService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ExchangeRateController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExchangeRateControllerTest {
    private final GifApiWrapper gifApiWrapper = new GifApiWrapper();
    private final GifApiImagesWrapper gifApiImagesWrapper = new GifApiImagesWrapper();
    private final GifApiOriginalWrapper gifApiOriginalWrapper = new GifApiOriginalWrapper();
    private final GifApiDataElementWrapper gifApiDataElementWrapper = new GifApiDataElementWrapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeRateService exchangeRateService;
    @MockBean
    private GifService gifService;

    @BeforeEach
    void setUp() {
        gifApiOriginalWrapper.setUrl("https://gif/rich");
        gifApiOriginalWrapper.setWidth("335");
        gifApiOriginalWrapper.setHeight("225");
        gifApiOriginalWrapper.setSize("2048");
        gifApiImagesWrapper.setOriginal(gifApiOriginalWrapper);
        gifApiDataElementWrapper.setImages(gifApiImagesWrapper);
        gifApiWrapper.setData(new GifApiDataElementWrapper[]{gifApiDataElementWrapper});
    }

    @Test
    public void whenLatestExchangeRateMoreThanYesterday_thenGifWithRichShouldBeReturned() throws Exception {
        when(exchangeRateService.isLatestCurrencyValueMoreThanYesterday("RUB")).thenReturn(true);
        when(gifService.getRandomGifByName("rich")).thenReturn(gifApiOriginalWrapper);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/exchange-rate/RUB")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"url\":\"https://gif/rich\"," +
                "\"size\":\"2048\"," +
                "\"width\":\"335\"," +
                "\"height\":\"225\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        verify(exchangeRateService, times(1)).isLatestCurrencyValueMoreThanYesterday("RUB");
        verify(gifService, times(1)).getRandomGifByName("rich");
    }

    @Test
    public void whenLatestExchangeRateLessThanYesterday_thenGifWithBrokeShouldBeReturned() throws Exception {
        when(exchangeRateService.isLatestCurrencyValueMoreThanYesterday("RUB")).thenReturn(false);
        gifApiOriginalWrapper.setUrl("https://gif/broke");
        when(gifService.getRandomGifByName("broke")).thenReturn(gifApiOriginalWrapper);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/exchange-rate/RUB")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"url\":\"https://gif/broke\"," +
                "\"size\":\"2048\"," +
                "\"width\":\"335\"," +
                "\"height\":\"225\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        verify(exchangeRateService, times(1)).isLatestCurrencyValueMoreThanYesterday("RUB");
        verify(gifService, times(1)).getRandomGifByName("broke");
    }
}
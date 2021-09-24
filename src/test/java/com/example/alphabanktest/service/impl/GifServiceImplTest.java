package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.dto.gif.GifApiDataElementWrapper;
import com.example.alphabanktest.dto.gif.GifApiImagesWrapper;
import com.example.alphabanktest.dto.gif.GifApiOriginalWrapper;
import com.example.alphabanktest.dto.gif.GifApiWrapper;
import com.example.alphabanktest.exceptions.GifNotFoundException;
import com.example.alphabanktest.feign.GifClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {GifServiceImpl.class})
class GifServiceImplTest {
    @Autowired
    private GifServiceImpl gifService;
    @MockBean
    private GifClient gifClient;
    private final GifApiWrapper gifApiWrapper = new GifApiWrapper();
    private final GifApiImagesWrapper gifApiImagesWrapper = new GifApiImagesWrapper();
    private final GifApiOriginalWrapper gifApiOriginalWrapper = new GifApiOriginalWrapper();
    private final GifApiDataElementWrapper gifApiDataElementWrapper = new GifApiDataElementWrapper();


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
    void whenGetRandomGifByName_thenGifApiOriginalWrapperShouldBeReturned() {
        when(gifClient.getGifApiWrapperByName("rich")).thenReturn(gifApiWrapper);

        GifApiOriginalWrapper expected = new GifApiOriginalWrapper();
        expected.setUrl("https://gif/rich");
        expected.setWidth("335");
        expected.setHeight("225");
        expected.setSize("2048");

        GifApiOriginalWrapper actual = gifService.getRandomGifByName("rich");

        assertEquals(expected, actual);

        verify(gifClient, times(2)).getGifApiWrapperByName("rich");
    }
    @Test

    void whenGifIsNotFound_thenGifNotFoundExceptionShouldBeThrown() {
        GifApiWrapper gifApiWrapper = new GifApiWrapper();
        gifApiWrapper.setData(new GifApiDataElementWrapper[0]);

        when(gifClient.getGifApiWrapperByName("rich")).thenReturn(gifApiWrapper);

        assertThrows(GifNotFoundException.class, () -> gifService.getRandomGifByName("rich"));

        verify(gifClient, times(1)).getGifApiWrapperByName("rich");
    }
}
package com.example.alphabanktest.service.impl;

import com.example.alphabanktest.dto.gif.GifApiWrapper;
import com.example.alphabanktest.dto.gif.GifDataElementWrapper;
import com.example.alphabanktest.dto.gif.GifImagesWrapper;
import com.example.alphabanktest.dto.gif.GifOriginalWrapper;
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
    private final GifApiWrapper gifApiWrapper = new GifApiWrapper();
    private final GifImagesWrapper gifImagesWrapper = new GifImagesWrapper();
    private final GifOriginalWrapper gifOriginalWrapper = new GifOriginalWrapper();
    private final GifDataElementWrapper gifDataElementWrapper = new GifDataElementWrapper();
    @Autowired
    private GifServiceImpl gifService;
    @MockBean
    private GifClient gifClient;

    @BeforeEach
    void setUp() {
        gifOriginalWrapper.setUrl("https://gif/rich");
        gifOriginalWrapper.setWidth("335");
        gifOriginalWrapper.setHeight("225");
        gifOriginalWrapper.setSize("2048");
        gifImagesWrapper.setOriginal(gifOriginalWrapper);
        gifDataElementWrapper.setImages(gifImagesWrapper);
        gifApiWrapper.setData(new GifDataElementWrapper[]{gifDataElementWrapper});
    }

    @Test
    void whenGetRandomGifByName_thenGifApiOriginalWrapperShouldBeReturned() {
        when(gifClient.getGifApiWrapperByGifName("rich")).thenReturn(gifApiWrapper);

        GifOriginalWrapper expected = new GifOriginalWrapper();
        expected.setUrl("https://gif/rich");
        expected.setWidth("335");
        expected.setHeight("225");
        expected.setSize("2048");

        GifOriginalWrapper actual = gifService.getRandomGifByName("rich");

        assertEquals(expected, actual);

        verify(gifClient, times(2)).getGifApiWrapperByGifName("rich");
    }

    @Test
    void whenGifIsNotFound_thenGifNotFoundExceptionShouldBeThrown() {
        GifApiWrapper gifApiWrapper = new GifApiWrapper();
        gifApiWrapper.setData(new GifDataElementWrapper[0]);

        when(gifClient.getGifApiWrapperByGifName("rich")).thenReturn(gifApiWrapper);

        assertThrows(GifNotFoundException.class, () -> gifService.getRandomGifByName("rich"));

        verify(gifClient, times(1)).getGifApiWrapperByGifName("rich");
    }
}
package com.example.alphabanktest.dto.gif;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GifApiDataElement {
    private String type;
    private GifsImagesWrapper images;
}

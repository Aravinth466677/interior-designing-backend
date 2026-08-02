package com.aravinth.life_designer_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GalleryImageResponse {

    private Long id;

    private String imageUrl;

    private Integer displayOrder;
}

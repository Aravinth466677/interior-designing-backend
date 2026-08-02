package com.aravinth.life_designer_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;

    private String title;

    private String description;

    private String category;

    private String location;

    private String heroImage;

    private List<GalleryImageResponse> gallery;

    private LocalDateTime createdAt;
}
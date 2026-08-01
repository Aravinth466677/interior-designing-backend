package com.aravinth.life_designer_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String message;

    private LocalDateTime createdAt;
}
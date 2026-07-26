package com.aravinth.life_designer_backend.dto.request;

import com.aravinth.life_designer_backend.entity.ContactStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContactStatusRequest {

    private ContactStatus status;

}
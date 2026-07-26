package com.aravinth.life_designer_backend.controller;

import com.aravinth.life_designer_backend.dto.request.CreateContactRequest;
import com.aravinth.life_designer_backend.dto.request.UpdateContactStatusRequest;
import com.aravinth.life_designer_backend.dto.response.ContactResponse;
import com.aravinth.life_designer_backend.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponse> create(
            @Valid @RequestBody CreateContactRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAll() {

        return ResponseEntity.ok(contactService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getById(@PathVariable Long id) {

        return ResponseEntity.ok(contactService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        contactService.delete(id);

        return ResponseEntity.ok("Contact deleted successfully");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ContactResponse> updateStatus(

            @PathVariable Long id,

            @RequestBody UpdateContactStatusRequest request) {

        return ResponseEntity.ok(
                contactService.updateStatus(id, request)
        );
    }
}
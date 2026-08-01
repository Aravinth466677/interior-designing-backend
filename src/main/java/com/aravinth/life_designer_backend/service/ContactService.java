package com.aravinth.life_designer_backend.service;

import com.aravinth.life_designer_backend.dto.request.CreateContactRequest;
import com.aravinth.life_designer_backend.dto.response.ContactResponse;
import com.aravinth.life_designer_backend.entity.Contact;
import com.aravinth.life_designer_backend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactResponse create(CreateContactRequest request) {

        Contact contact = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .message(request.getMessage())
                .build();

        return map(contactRepository.save(contact));
    }

    public List<ContactResponse> getAll() {

        return contactRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public ContactResponse getById(Long id) {

        return map(contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found")));
    }

    public void delete(Long id) {

        contactRepository.deleteById(id);
    }

    private ContactResponse map(Contact contact) {

        return ContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .message(contact.getMessage())
                .createdAt(contact.getCreatedAt())
                .build();
    }

}

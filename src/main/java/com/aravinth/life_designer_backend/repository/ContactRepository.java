package com.aravinth.life_designer_backend.repository;

import com.aravinth.life_designer_backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}

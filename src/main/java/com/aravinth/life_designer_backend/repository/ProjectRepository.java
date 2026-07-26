package com.aravinth.life_designer_backend.repository;

import com.aravinth.life_designer_backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}

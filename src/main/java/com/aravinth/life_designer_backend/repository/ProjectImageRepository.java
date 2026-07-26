package com.aravinth.life_designer_backend.repository;

import com.aravinth.life_designer_backend.entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectImageRepository extends JpaRepository<ProjectImage,Long> {
    List<ProjectImage> findByProjectIdOrderByDisplayOrderAsc(Long projectId);
}

package com.aravinth.life_designer_backend.service;

import com.aravinth.life_designer_backend.dto.request.CreateProjectRequest;
import com.aravinth.life_designer_backend.dto.request.UpdateProjectRequest;
import com.aravinth.life_designer_backend.dto.response.ProjectResponse;
import com.aravinth.life_designer_backend.entity.Project;
import com.aravinth.life_designer_backend.entity.ProjectImage;
import com.aravinth.life_designer_backend.repository.ProjectImageRepository;
import com.aravinth.life_designer_backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CloudinaryService cloudinaryService;
    private final ProjectImageRepository projectImageRepository;

    public ProjectService(ProjectRepository projectRepository,
                          CloudinaryService cloudinaryService,
                          ProjectImageRepository projectImageRepository) {
        this.projectRepository = projectRepository;
        this.cloudinaryService = cloudinaryService;
        this.projectImageRepository=projectImageRepository;
    }

    private ProjectResponse mapToResponse(Project project) {

        List<String> gallery = project.getGallery()
                .stream()
                .sorted(Comparator.comparing(ProjectImage::getDisplayOrder))
                .map(ProjectImage::getImageUrl)
                .toList();

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .category(project.getCategory())
                .location(project.getLocation())
                .heroImage(project.getHeroImage())
                .coverImage(project.getCoverImage())
                .gallery(gallery)
                .createdAt(project.getCreatedAt())
                .build();
    }

    public ProjectResponse createProject(
            CreateProjectRequest request,
            MultipartFile heroImage,
            MultipartFile coverImage) throws IOException {
        String heroImageUrl = cloudinaryService.uploadImage(heroImage);

        String coverImageUrl;

        if (coverImage == null || coverImage.isEmpty()) {
            coverImageUrl = heroImageUrl;
        } else {
            coverImageUrl = cloudinaryService.uploadImage(coverImage);
        }

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation())
                .heroImage(heroImageUrl)
                .coverImage(coverImageUrl)
                .build();

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }



    public List<ProjectResponse> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return mapToResponse(project);
    }

    public ProjectResponse updateProject(Long id, UpdateProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setCategory(request.getCategory());
        project.setLocation(request.getLocation());
        project.setHeroImage(request.getHeroImage());

        if (request.getCoverImage() == null || request.getCoverImage().isBlank()) {
            project.setCoverImage(request.getHeroImage());
        } else {
            project.setCoverImage(request.getCoverImage());
        }

        Project updatedProject = projectRepository.save(project);

        return mapToResponse(updatedProject);
    }

    public void deleteProject(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectRepository.delete(project);
    }

    public void uploadGallery(
            Long projectId,
            List<MultipartFile> images) throws IOException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        int order = project.getGallery().size();

        for (MultipartFile image : images) {

            String imageUrl = cloudinaryService.uploadImage(image);

            ProjectImage projectImage = ProjectImage.builder()
                    .imageUrl(imageUrl)
                    .displayOrder(++order)
                    .project(project)
                    .build();

            project.getGallery().add(projectImage);
        }

        projectRepository.save(project);
    }

    public void deleteGalleryImage(Long imageId) throws IOException {

        ProjectImage image = projectImageRepository.findById(imageId)
                .orElseThrow(() ->
                        new RuntimeException("Gallery image not found"));

        cloudinaryService.deleteImage(image.getImageUrl());

        projectImageRepository.delete(image);
    }


}
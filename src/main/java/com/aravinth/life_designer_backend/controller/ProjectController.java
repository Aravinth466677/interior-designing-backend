package com.aravinth.life_designer_backend.controller;

import com.aravinth.life_designer_backend.dto.request.CreateProjectRequest;
import com.aravinth.life_designer_backend.dto.request.UpdateProjectRequest;
import com.aravinth.life_designer_backend.dto.response.ProjectResponse;
import com.aravinth.life_designer_backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProjectResponse> createProject(

            @ModelAttribute CreateProjectRequest request,

            @RequestParam("heroImage") MultipartFile heroImage,

            @RequestParam(value = "coverImage", required = false)
            MultipartFile coverImage

    ) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(
                        request,
                        heroImage,
                        coverImage));
    }

    @PostMapping(
            value = "/{id}/gallery",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadGallery(

            @PathVariable Long id,

            @RequestParam("images")
            List<MultipartFile> images

    ) throws IOException {

        projectService.uploadGallery(id, images);

        return ResponseEntity.ok("Gallery uploaded successfully.");
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {

        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request) {

        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);

        return ResponseEntity.ok("Project deleted successfully");
    }

    @DeleteMapping("/gallery/{imageId}")
    public ResponseEntity<String> deleteGalleryImage(
            @PathVariable Long imageId) throws IOException {

        projectService.deleteGalleryImage(imageId);

        return ResponseEntity.ok("Gallery image deleted successfully.");
    }
}
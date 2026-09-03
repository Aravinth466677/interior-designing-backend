package com.aravinth.life_designer_backend.controller;

import com.aravinth.life_designer_backend.dto.response.ProjectResponse;
import com.aravinth.life_designer_backend.service.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SitemapController {

    private final ProjectService projectService;

    public SitemapController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(
            value = "/sitemap.xml",
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> getSitemap() {

        List<ProjectResponse> projects = projectService.getAllProjects();

        StringBuilder sitemap = new StringBuilder();

        sitemap.append("""
                <?xml version="1.0" encoding="UTF-8"?>
                <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                """);

        // Homepage
        sitemap.append("""
                    <url>
                        <loc>https://life-desighner.vercel.app/</loc>
                    </url>
                """);

        // Projects page
        sitemap.append("""
                    <url>
                        <loc>https://life-desighner.vercel.app/projects</loc>
                    </url>
                """);

        // Individual projects
        for (ProjectResponse project : projects) {

            sitemap.append("""
                        <url>
                            <loc>https://life-desighner.vercel.app/projects/%d</loc>
                        </url>
                    """.formatted(project.getId()));
        }

        // Contact page
        sitemap.append("""
                    <url>
                        <loc>https://life-desighner.vercel.app/contact</loc>
                    </url>
                """);

        sitemap.append("</urlset>");

        return ResponseEntity.ok(sitemap.toString());
    }
}
package com.aravinth.life_designer_backend.config;

import com.aravinth.life_designer_backend.entity.Project;
import com.aravinth.life_designer_backend.entity.ProjectImage;
import com.aravinth.life_designer_backend.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProjectSeeder implements CommandLineRunner {

    private final ProjectRepository projectRepository;

    public ProjectSeeder(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) {
        if (projectRepository.count() == 0) {
            Project p1 = Project.builder()
                    .title("Modern Minimalist Residence")
                    .description("A contemporary residential interior featuring open floor plans, clean lines, and natural wood accents.")
                    .category("Residential")
                    .location("Coimbatore, Tamil Nadu")
                    .heroImage("https://images.unsplash.com/photo-1600210492486-724fe5c67fb0")
                    .coverImage("https://images.unsplash.com/photo-1600585154340-be6161a56a0c")
                    .build();

            ProjectImage img1 = ProjectImage.builder()
                    .imageUrl("https://images.unsplash.com/photo-1600210492486-724fe5c67fb0")
                    .displayOrder(1)
                    .project(p1)
                    .build();
            ProjectImage img2 = ProjectImage.builder()
                    .imageUrl("https://images.unsplash.com/photo-1600585154340-be6161a56a0c")
                    .displayOrder(2)
                    .project(p1)
                    .build();
            p1.getGallery().addAll(List.of(img1, img2));

            Project p2 = Project.builder()
                    .title("Urban Executive Suite")
                    .description("Sleek commercial workspace design featuring ergonomic furniture, acoustic panels, and smart lighting.")
                    .category("Commercial")
                    .location("Chennai, Tamil Nadu")
                    .heroImage("https://images.unsplash.com/photo-1618221195710-dd6b41faaea6")
                    .coverImage("https://images.unsplash.com/photo-1618219908412-a29a1bb7b86e")
                    .build();

            ProjectImage img3 = ProjectImage.builder()
                    .imageUrl("https://images.unsplash.com/photo-1618221195710-dd6b41faaea6")
                    .displayOrder(1)
                    .project(p2)
                    .build();
            p2.getGallery().add(img3);

            Project p3 = Project.builder()
                    .title("Zen Sanctuary Villa")
                    .description("Peaceful luxury villa interior blending Japanese minimalism with modern Scandinavian aesthetics.")
                    .category("Residential")
                    .location("Bengaluru, Karnataka")
                    .heroImage("https://images.unsplash.com/photo-1600607687939-ce8a6c25118c")
                    .coverImage("https://images.unsplash.com/photo-1600566753376-12c8ab7fb75b")
                    .build();

            ProjectImage img4 = ProjectImage.builder()
                    .imageUrl("https://images.unsplash.com/photo-1600607687939-ce8a6c25118c")
                    .displayOrder(1)
                    .project(p3)
                    .build();
            p3.getGallery().add(img4);

            projectRepository.saveAll(List.of(p1, p2, p3));
            System.out.println("Default Projects & Gallery Images Seeded Successfully!");
        }
    }
}

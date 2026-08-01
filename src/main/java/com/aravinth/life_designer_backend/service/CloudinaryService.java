package com.aravinth.life_designer_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private static final long MAX_UPLOAD_SIZE = 30L * 1024 * 1024;
    private static final long TARGET_UPLOAD_SIZE = 10L * 1024 * 1024;
    private static final long MAX_PIXELS = 50_000_000L;

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {

        validateImage(file);

        try {
            byte[] imageBytes = compressIfNeeded(file);

            Map<?, ?> result = cloudinary.uploader().upload(
                    imageBytes,
                    ObjectUtils.asMap(
                            "public_id", UUID.randomUUID().toString(),
                            "folder", "life-designer/projects"
                    )
            );

            if (result != null && result.get("secure_url") != null) {
                return result.get("secure_url").toString();
            }
        } catch (Exception ex) {
            System.err.println("Cloudinary upload warning: " + ex.getMessage() + ". Using fallback image URL.");
        }

        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.jpg";
        return "https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?filename=" + fileName;
    }

    private void validateImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Image is required.");
        }

        if (file.getSize() > MAX_UPLOAD_SIZE) {
            throw new RuntimeException("Maximum upload size is 30 MB.");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                        || contentType.equals("image/png")
                        || contentType.equals("image/webp"))) {

            throw new RuntimeException("Only JPG, PNG and WEBP images are allowed.");
        }

        BufferedImage image = ImageIO.read(file.getInputStream());

        if (image == null) {
            throw new RuntimeException("Invalid image.");
        }

        long pixels = (long) image.getWidth() * image.getHeight();

        if (pixels > MAX_PIXELS) {
            throw new RuntimeException("Maximum image resolution is 50 MP.");
        }
    }

    public void deleteImage(String imageUrl) throws IOException {
        try {
            if (imageUrl != null && imageUrl.contains("life-designer/")) {
                String publicId = imageUrl.substring(
                        imageUrl.indexOf("life-designer/")
                );

                if (publicId.contains(".")) {
                    publicId = publicId.substring(
                            0,
                            publicId.lastIndexOf(".")
                    );
                }

                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (Exception ex) {
            System.err.println("Cloudinary delete warning: " + ex.getMessage());
        }
    }

    private byte[] compressIfNeeded(MultipartFile file) throws IOException {

        if (file.getSize() <= TARGET_UPLOAD_SIZE) {
            return file.getBytes();
        }

        BufferedImage image = ImageIO.read(file.getInputStream());

        String format = switch (file.getContentType()) {
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            default -> "jpg";
        };

        float[] qualities = {0.85f, 0.75f, 0.65f};

        int[][] sizes = {
                {2500, 2500},
                {2000, 2000},
                {1600, 1600}
        };

        for (int i = 0; i < qualities.length; i++) {

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            Thumbnails.of(image)
                    .size(sizes[i][0], sizes[i][1])
                    .outputQuality(qualities[i])
                    .outputFormat(format)
                    .toOutputStream(output);

            if (output.size() <= TARGET_UPLOAD_SIZE) {
                return output.toByteArray();
            }
        }

        throw new RuntimeException(
                "Unable to compress image below 10 MB."
        );
    }
}
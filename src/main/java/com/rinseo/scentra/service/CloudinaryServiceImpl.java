package com.rinseo.scentra.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rinseo.scentra.configuration.CloudinaryConfig;
import com.rinseo.scentra.utils.FileNameSanitizer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {
    private final FileNameSanitizer fileNameSanitizer;
    private final CloudinaryConfig cloudinaryConfig;

    /**
     * Upload image file to cloudinary
     * Must provide a valid cloudinary API key via .env or system environment variable
     *
     * @param file   MultipartFile
     * @param folder The folder structure in cloudinary
     * @return The public id of the uploaded image
     */
    @Override
    public String uploadImageFile(MultipartFile file, String folder) {
        if (file == null) {
            throw new RuntimeException("File is required");
        }
        // Test for content type
        if (!fileNameSanitizer.test(Objects.requireNonNull(file.getContentType()))) {
            throw new RuntimeException("File must be an image");
        }

        // Get cloudinary instance
        Cloudinary cloudinary = cloudinaryConfig.getCloudinary("CLOUDINARY_URL");

        // Sanitize file name
        String sanitizedName = fileNameSanitizer.sanitize(Objects.requireNonNull(file.getOriginalFilename()));
        // Generate unique public id
        String publicId = sanitizedName.split("\\.")[0].concat("_").concat(String.valueOf(System.currentTimeMillis()));
        try {
            Map params = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true,
                    "folder", folder,
                    "public_id", publicId
            );
            log.info(cloudinary.uploader().upload(file.getBytes(), params).toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image");
        }
        return publicId;
    }
}

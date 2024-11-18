package com.rinseo.scentra.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImageFile(MultipartFile file, String folder);
}

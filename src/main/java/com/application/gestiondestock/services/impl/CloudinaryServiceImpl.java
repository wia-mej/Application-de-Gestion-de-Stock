package com.application.gestiondestock.services.impl;

import com.application.gestiondestock.services.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String savePhoto(InputStream photo, String title) throws IOException {
        if (photo == null || title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Le flux photo et le titre ne doivent pas être null ou vides.");
        }

        try {
            return cloudinary.uploader()
                    .upload(photo, ObjectUtils.asMap(
                            "public_id", title,
                            "resource_type", "image",
                            "folder", "gestion_stock"
                    ))
                    .get("secure_url")
                    .toString();
        } catch (Exception e) {
            log.error("Erreur lors de l'upload de la photo : {}", e.getMessage());
            throw new IOException("Erreur lors de l'upload de la photo sur Cloudinary", e);
        }
    }

    @Override
    public String deletePhoto(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            throw new IllegalArgumentException("L'ID public ne doit pas être null ou vide.");
        }

        try {
            return cloudinary.uploader()
                    .destroy(publicId, ObjectUtils.emptyMap())
                    .get("result")
                    .toString();
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la photo : {}", e.getMessage());
            throw new IOException("Erreur lors de la suppression de la photo de Cloudinary", e);
        }
    }

    @Override
    public String updatePhoto(String publicId, InputStream newPhoto) throws IOException {
        if (publicId == null || publicId.isEmpty() || newPhoto == null) {
            throw new IllegalArgumentException("L'ID public et la nouvelle photo doivent être valides.");
        }

        try {
            return cloudinary.uploader()
                    .upload(newPhoto, ObjectUtils.asMap(
                            "public_id", publicId,
                            "resource_type", "image",
                            "overwrite", true
                    ))
                    .get("secure_url")
                    .toString();
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la photo : {}", e.getMessage());
            throw new IOException("Erreur lors de la mise à jour de la photo sur Cloudinary", e);
        }
    }
}
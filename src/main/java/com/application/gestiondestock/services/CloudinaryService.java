package com.application.gestiondestock.services;

import java.io.IOException;
import java.io.InputStream;

public interface CloudinaryService {


    String savePhoto(InputStream photo, String title) throws IOException;

    String deletePhoto(String publicId) throws IOException;

    String updatePhoto(String publicId, InputStream photo) throws IOException;

}

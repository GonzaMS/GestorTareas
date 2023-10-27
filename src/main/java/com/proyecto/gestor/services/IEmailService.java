package com.proyecto.gestor.services;

import java.io.File;

public interface IEmailService {
    void sendEmail(String[] to, String subject, String content);

    void sendEmailWithFile(String[] to, String subject, String content, File file);
}

package org.example.services;

import jakarta.transaction.Transactional;
import org.example.database.FileEntity;
import org.example.database.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;

@Service
public class StorageService {

    @Autowired
    private FileRepository repo;

    @Transactional
    public FileEntity store(MultipartFile file) throws Exception {

        byte[] bytes = file.getBytes();
        String sha256 = sha256Hex(bytes);

        return repo.findById(sha256)
                .orElseGet(() -> {
                    FileEntity e = FileEntity.builder()
                            .id(sha256)
                            .filename(file.getOriginalFilename())
                            .contentType(file.getContentType())
                            .data(bytes)
                            .build();
                    return repo.save(e);
                });
    }

    public FileEntity get(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Файл не найден"));
    }

    private static String sha256Hex(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Не получилось SHA‑256", e);
        }
    }
}

package org.example.controllers;

import org.example.database.FileEntity;
import org.example.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private StorageService storage;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            FileEntity saved = storage.store(file);
            return ResponseEntity.ok("ID файла (SHA‑256): " + saved.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> download(@PathVariable String id) {
        FileEntity f = storage.get(id);
        return ResponseEntity.ok()
                .header("Content-Type", f.getContentType())
                .header("Content-Disposition", "attachment; filename=\"" + f.getFilename() + "\"")
                .body(f.getData());
    }
}


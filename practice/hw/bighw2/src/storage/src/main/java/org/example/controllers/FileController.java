package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.database.MyFileEntity;
import org.example.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@Tag(name = "Storage", description = "хранилище файлов")
public class FileController {

    @Autowired
    private StorageService storage;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить файл")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            MyFileEntity saved = storage.store(file);
            return ResponseEntity.ok("ID файла (SHA‑256): " + saved.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Выгрузить файл")
    public ResponseEntity<?> download(@PathVariable String id) {
        MyFileEntity f = storage.get(id);
        return ResponseEntity.ok()
                .header("Content-Type", f.getContentType())
                .header("Content-Disposition", "attachment; filename=\"" + f.getFilename() + "\"")
                .body(f.getData());
    }
}


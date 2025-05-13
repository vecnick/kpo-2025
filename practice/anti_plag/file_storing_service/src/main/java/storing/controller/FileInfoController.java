package storing.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import storing.record.FileInfoParams;
import storing.service.FileInfoService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
public class FileInfoController {
    private final FileInfoService fileInfoService;

    public  FileInfoController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    @Operation(summary = "Получить описание всех файлов")
    @GetMapping("/files")
    public ResponseEntity<List<FileInfoParams>> getAll() {
        return ResponseEntity.ok(
                fileInfoService.getAll().stream().map(FileInfoParams::new).toList()
        );
    }

    @Operation(summary = "Получить описание файла по его id")
    @GetMapping("/files/{id}")
    public ResponseEntity<FileInfoParams> getById(int id) {
        return fileInfoService.getById(id).map(
                fileInfo -> ResponseEntity.ok(new FileInfoParams(fileInfo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить id файла по его хэшу")
    @GetMapping("/files/{hash}/id")
    public ResponseEntity<Integer> getIdByHash(String hash) {
        return fileInfoService.getIdByHash(hash).map(
                id -> ResponseEntity.ok(id))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить путь до файла по его id")
    @GetMapping("/files/{id}/location")
    public ResponseEntity<String> getLocationById(int id) {
        return fileInfoService.getLocationById(id).map(
                location -> ResponseEntity.ok(location))
                .orElse(ResponseEntity.notFound().build());
    }
}

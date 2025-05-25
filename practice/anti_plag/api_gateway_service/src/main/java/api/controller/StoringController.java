package api.controller;

import api.interfaces.IStoringGrpcImpl;
import api.record.FileInfoParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class StoringController {

    private final IStoringGrpcImpl storingGrpc;

    public StoringController(IStoringGrpcImpl storingGrpc) {
        this.storingGrpc = storingGrpc;
    }

    @Operation(summary = "Добавить файл")
    @PutMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> save(@RequestPart("file") MultipartFile file) {
        String filename;
        String contentString;

        try {
            filename = file.getOriginalFilename();
            contentString = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Не удалось считать содержимое файла - StoringController");
            return ResponseEntity.badRequest().build();
        }

        return storingGrpc.saveFileInfo(filename, contentString).map(
                id -> ResponseEntity.ok(id))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Удалить файл")
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteById(int id) {
        return storingGrpc.deleteFileInfoById(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Получить описание всех файлов")
    @GetMapping("/files")
    public ResponseEntity<List<FileInfoParams>> getAll() {
        return storingGrpc.getAll().map(
                filesInfo -> ResponseEntity.ok(filesInfo))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить описание файла по его id")
    @GetMapping("/files/{id}")
    public ResponseEntity<FileInfoParams> getById(int id) {
        return storingGrpc.getById(id).map(
                fileInfo -> ResponseEntity.ok(fileInfo))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить путь до файла по его id")
    @GetMapping("/files/{id}/location")
    public ResponseEntity<String> getLocationById(int id) {
        return storingGrpc.getLocationById(id).map(
                location -> ResponseEntity.ok(location))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить содержимое файла по его id")
    @GetMapping("/files/{id}/text")
    public ResponseEntity<String> getFileTextById(int id) {
        return storingGrpc.getFileTextById(id).map(
                text -> ResponseEntity.ok(text))
                .orElse(ResponseEntity.badRequest().build());
    }
}
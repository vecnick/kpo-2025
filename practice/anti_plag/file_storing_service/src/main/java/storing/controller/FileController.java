package storing.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import storing.interfaces.IFileService;
import storing.service.FileService;

@RestController
public class FileController {
    private final IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Добавить файл")
    @PutMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> save(@RequestPart("file") MultipartFile file) {
        return fileService.save(file).map(
            id -> ResponseEntity.ok(id))
            .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Удалить файл")
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteById(int id) {
        return fileService.deleteById(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }
}
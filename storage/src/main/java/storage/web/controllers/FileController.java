package storage.web.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import storage.domains.FileEntity;
import storage.facades.FileFacade;
import storage.web.dtos.FileResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/files")
@Tag(name = "Микросервис хранения файлов", description = "Хранение данных")
public class FileController {

    private FileFacade fileFacade;

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить файл")
    public ResponseEntity<FileResponse> uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            var fileEntity = fileFacade.saveFile(file);
            var dto = fileToDto(fileEntity);
            return ResponseEntity.ok().body(dto);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить информацию о файле")
    public ResponseEntity<FileResponse> getFile(@PathVariable Integer id) {
        var fileOptional = fileFacade.getFileById(id);
        return fileOptional.map(fileEntity -> ResponseEntity.ok().body(fileToDto(fileEntity)))
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}/content")
    @Operation(summary = "Получить содержимое файла")
    public ResponseEntity<String> getFileContent(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(fileFacade.getContentById(id));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private FileResponse fileToDto(FileEntity fileEntity) {
        return new FileResponse(fileEntity.getId(), fileEntity.getName(), fileEntity.getPath(), fileEntity.getHash());
    }
}
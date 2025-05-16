package analysis.controller;

import analysis.entity.TextAnalys;
import analysis.interfaces.IAnalysService;
import analysis.record.TextAnalysParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import storing.interfaces.IFileInfoService;
import storing.record.FileInfoParams;

import java.util.List;

@RestController
public class AnalysController {
    private final IAnalysService analysService;

    public  AnalysController(IAnalysService analysService) {
        this.analysService = analysService;
    }

    @Operation(summary = "Получить описание всех анализов файлов")
    @GetMapping("/analys")
    public ResponseEntity<List<TextAnalysParams>> getAll() {
        return ResponseEntity.ok(
                analysService.getAll().stream().map(TextAnalysParams::new).toList()
        );
    }

    @Operation(summary = "Получить описание анализа файла по его id")
    @GetMapping("/analys/{fileId}")
    public ResponseEntity<TextAnalysParams> getByFileId(int fileId) {
        return analysService.getByFileId(fileId).map(
                textAnalys -> ResponseEntity.ok(new TextAnalysParams(textAnalys)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить путь до фотографии WordCloud по id файла")
    @GetMapping("/analys/{fileId}/path")
    public ResponseEntity<String> getPathByFileId(int fileId) {
        return analysService.getPathByFileId(fileId).map(
                path -> ResponseEntity.ok(path))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Провести анализ файла")
    @PutMapping(value = "/analys", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TextAnalys> startAnalys(@RequestPart("fileId") int fileId) {
        return analysService.startAnalys(fileId).map(
                textAnalys -> ResponseEntity.ok(textAnalys))
                .orElse(ResponseEntity.badRequest().build());
    }

//    @Operation(summary = "Получить содержимое файла по его id")
//    @GetMapping("/files/{id}/text")
//    public ResponseEntity<String> getFileTextById(int id) {
//        return fileInfoService.getFileTextById(id).map(
//                        text -> ResponseEntity.ok(text))
//                .orElse(ResponseEntity.badRequest().build());
//    }
}

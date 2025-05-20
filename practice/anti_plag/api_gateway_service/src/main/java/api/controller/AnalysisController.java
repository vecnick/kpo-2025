package api.controller;

import api.interfaces.IAnalysisGrpcImpl;
import api.record.TextAnalysParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalysisController {

    private final IAnalysisGrpcImpl analysisGrpc;

    public AnalysisController(IAnalysisGrpcImpl analysisGrpc) {
        this.analysisGrpc = analysisGrpc;
    }

    @Operation(summary = "Получить описание всех анализов файлов")
    @GetMapping("/analys")
    public ResponseEntity<List<TextAnalysParams>> getAll() {
        return analysisGrpc.getAll().map(
                textsAnalys -> ResponseEntity.ok(textsAnalys))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить описание анализа файла по его id")
    @GetMapping("/analys/{fileId}")
    public ResponseEntity<TextAnalysParams> getByFileId(int fileId) {
        return analysisGrpc.getByFileId(fileId).map(
                textAnalys -> ResponseEntity.ok(textAnalys))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить путь до фотографии WordCloud по id файла")
    @GetMapping("/analys/{fileId}/path")
    public ResponseEntity<String> getPathByFileId(int fileId) {
        return analysisGrpc.getPathByFileId(fileId).map(
                path -> ResponseEntity.ok(path))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Получить картинку WordCloud по id файла")
    @GetMapping(value = "/analys/{id}/WordCloud", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getWordCloudPic(int fileId) {
        return analysisGrpc.getWordCloudPic(fileId).map(
                bytes -> ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes))
                .orElse(ResponseEntity.badRequest().build());
    }
}

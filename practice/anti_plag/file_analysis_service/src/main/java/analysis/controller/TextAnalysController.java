package analysis.controller;

import analysis.interfaces.ITextAnalysService;
import analysis.record.TextAnalysParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TextAnalysController {

    private final ITextAnalysService textAnalysService;

    public TextAnalysController(ITextAnalysService textAnalysService) {
        this.textAnalysService = textAnalysService;
    }

    @Operation(summary = "Получить описание всех анализов файлов")
    @GetMapping("/analys")
    public ResponseEntity<List<TextAnalysParams>> getAll() {
        return ResponseEntity.ok(
                textAnalysService.getAll().stream().map(TextAnalysParams::new).toList()
        );
    }

    @Operation(summary = "Получить описание анализа файла по его id")
    @GetMapping("/analys/{fileId}")
    public ResponseEntity<TextAnalysParams> getByFileId(int fileId) {
        return textAnalysService.getByFileId(fileId).map(
                textAnalys -> ResponseEntity.ok(new TextAnalysParams(textAnalys)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить путь до фотографии WordCloud по id файла")
    @GetMapping("/analys/{fileId}/path")
    public ResponseEntity<String> getPathByFileId(int fileId) {
        return textAnalysService.getPathByFileId(fileId).map(
                path -> ResponseEntity.ok(path))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить картинку WordCloud по id файла")
    @GetMapping(value = "/analys/{id}/WordCloud", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getWordCloudPic(int fileId) {
        return textAnalysService.getWordCloudPic(fileId).map(
                bytes -> ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes))
                .orElse(ResponseEntity.badRequest().build());
    }
}

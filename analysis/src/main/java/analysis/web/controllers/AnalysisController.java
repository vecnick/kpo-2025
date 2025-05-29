package analysis.web.controllers;

import analysis.services.AnalysisService;
import analysis.web.dtos.StatisticResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Анализ данных", description = "Анализ данных")
@RequestMapping("/api/analysis")
public class AnalysisController {
    private AnalysisService analysisService;

    @GetMapping("{fileId}")
    @Operation(summary = "статистика файла")
    public ResponseEntity<StatisticResponse> analyze(@PathVariable Integer fileId) {
        try {
            var result = analysisService.analyze(fileId);
            return ResponseEntity.ok(new StatisticResponse(
                    result.getFileId(),
                    result.getParagraphs(),
                    result.getWords(),
                    result.getSymbols()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{fileId}/wordcloud", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "облако слов")
    public ResponseEntity<byte[]> generateWordCloud(@PathVariable Integer fileId) {
        try {
            byte[] image = analysisService.generateWordCloud(fileId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{fileId}/plagiarised")
    @Operation(summary = "Проверить файл на существование идентичного")
    public ResponseEntity<Boolean> checkPlagiarised(@PathVariable Integer fileId) {
        try {
            return ResponseEntity.ok(analysisService.checkPlagiarise(fileId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
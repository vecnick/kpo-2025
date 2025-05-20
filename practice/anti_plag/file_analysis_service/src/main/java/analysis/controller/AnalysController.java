package analysis.controller;

import analysis.entity.TextAnalys;
import analysis.interfaces.IAnalysService;
import analysis.record.TextAnalysParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AnalysController {
    private final IAnalysService analysService;

    public AnalysController(IAnalysService analysService) {
        this.analysService = analysService;
    }

    @Operation(summary = "Провести анализ файла")
    @PutMapping(value = "/analys", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TextAnalys> startAnalys(@RequestPart("fileId") int fileId) {
        return analysService.startAnalys(fileId).map(
                textAnalys -> ResponseEntity.ok(textAnalys))
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Удалить анализ файла")
    @DeleteMapping("/analys/{fileId}")
    public ResponseEntity<Void> deleteByFileId(int fileId) {
        return analysService.deleteAnalys(fileId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }
}

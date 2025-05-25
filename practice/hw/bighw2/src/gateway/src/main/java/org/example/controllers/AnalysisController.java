package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.FilesMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.client.RestTemplate;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/analysis")
@Tag(name = "Analysis", description = "Обращение к сервису анализа")
@RequiredArgsConstructor
public class AnalysisController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FilesMappingService filesMappingService;

    @Value("http://analysis:8080")
    private String analServiceUrl;

    @GetMapping("/{name}")
    @Operation(summary = "Запрос в сервис анализа")
    public ResponseEntity<?> proxyToAnalysis(@PathVariable String name) {
        String id;
        try {
            id = filesMappingService.getHashByName(name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        String analysisUrl = analServiceUrl + "/api/analysis/" + id;

        ResponseEntity<byte[]> response = restTemplate.exchange(
                analysisUrl,
                HttpMethod.GET,
                null,
                byte[].class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return ResponseEntity.status(response.getStatusCode()).body(null);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(response.getBody());
    }
}

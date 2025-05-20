package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@Tag(name = "Analysis", description = "анализ файлов")
public class AnalysisController {

    @Value("http://storage:8081")
    private String fileServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    @Operation(summary = "Проанализировать файл")
    public ResponseEntity<?> analysis(@PathVariable String id) {
        String url = fileServiceUrl + "/api/files/" + id;

        ResponseEntity<byte[]> fileResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                byte[].class
        );

        if (fileResponse.getStatusCode() != HttpStatus.OK || fileResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String text = new String(fileResponse.getBody(), StandardCharsets.UTF_8);

        Map<String, Object> payload = new HashMap<>();
        payload.put("format", "png");
        payload.put("width", 600);
        payload.put("height", 400);
        payload.put("fontScale", 15);
        payload.put("scale", "linear");
        payload.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        String quickChartUrl = "https://quickchart.io/word-cloud";

        ResponseEntity<byte[]> cloudResponse = restTemplate.exchange(
                quickChartUrl,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        if (cloudResponse.getStatusCode() != HttpStatus.OK || cloudResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(cloudResponse.getBody());
    }
}

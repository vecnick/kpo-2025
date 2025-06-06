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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@Tag(name = "Analysis", description = "анализ файлов")
public class AnalysisController {

    @Value("http://storage:8080")
    private String fileServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    @Operation(summary = "Проанализировать файл")
    public ResponseEntity<?> analysis(@PathVariable String id) {
        String url_fs = fileServiceUrl + "/api/files/" + id;

        ResponseEntity<byte[]> fileResponse = restTemplate.exchange(
                url_fs,
                HttpMethod.GET,
                null,
                byte[].class
        );

        if (fileResponse.getStatusCode() != HttpStatus.OK || fileResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String text = new String(fileResponse.getBody(), StandardCharsets.UTF_8);

        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("text", text);
            payload.put("format", "png");
            payload.put("width", 800);
            payload.put("height", 600);

            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    "https://quickchart.io/wordcloud",
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.IMAGE_PNG);
                return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.OK);
            } else {
                return ResponseEntity.status(response.getStatusCode()).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

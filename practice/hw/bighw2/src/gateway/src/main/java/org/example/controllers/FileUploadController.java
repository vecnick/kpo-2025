package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@Tag(name = "Загрузка", description = "Загрузка файлов в систему")
public class FileUploadController {

    @Value("http://storage:8080")
    private String fileServiceUrl;

    private final RestTemplate restTemplate;

    public FileUploadController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    @Operation(summary = "Загрузить файл")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            HttpHeaders fileHeaders = new HttpHeaders();
            fileHeaders.setContentType(MediaType.TEXT_PLAIN);

            HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileHeaders);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(fileServiceUrl + "/store", request, String.class);

            return ResponseEntity.ok("Ответ от хранилища: " + response.getBody());

        } catch (HttpStatusCodeException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body("Ошибка от хранилища " + e.getResponseBodyAsString());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Внутренняя ошибка: " + e.getMessage());
        }
    }
}


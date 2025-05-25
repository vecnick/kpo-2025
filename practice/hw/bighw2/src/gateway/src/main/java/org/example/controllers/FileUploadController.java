package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.services.FilesMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/upload")
@Tag(name = "Загрузка", description = "Загрузка файлов в систему")
public class FileUploadController {

    @Value("http://storage:8080")
    private String fileServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    FilesMappingService filesMappingService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузить файл")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            Resource fileAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            body.add("file", fileAsResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    fileServiceUrl + "/api/files/upload",
                    requestEntity,
                    String.class
            );

            String responseBody = response.getBody();

            try {
                if (responseBody != null && responseBody.contains(":")) {
                    String id = responseBody.split(":")[1].trim();
                    filesMappingService.addFile(fileAsResource.getFilename(), id);
                } else {
                    throw new RuntimeException("Некорректный ID в ответе от хранилища, ответ хранилища:\n" + responseBody);
                }
            } catch (Exception e) {
                return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }

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


package org.example.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/download")
@Tag(name = "Скачивание файлов", description = "Скачивание файлов по ID")
public class FileDownloadController {

    @Value("http://storage:8081")
    private String fileServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    @Operation(summary = "Скачать файл")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        String url = fileServiceUrl + "/api/files/" + id;

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                byte[].class
        );

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

}

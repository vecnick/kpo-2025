package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/process")
public class AnalysisController {

    @PostMapping
    public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);

        // Считаем символы/слова/абзацы
        long paragraphs = content.split("\n\n").length;
        long words = content.split("\\s+").length;
        long characters = content.length();

        return ResponseEntity.ok("Paragraphs: " + paragraphs + ", Words: " + words + ", Characters: " + characters);
    }
}


package api.controller;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoadController {
}
/*

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




    @Operation(summary = "Добавить файл")
    @PutMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> save(@RequestPart("file") MultipartFile file) {
        return fileService.save(file).map(
            id -> ResponseEntity.ok(id))
            .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Удалить файл")
    @DeleteMapping("/files/{id}")
    public ResponseEntity<Void> deleteById(int id) {
        return fileService.deleteById(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.badRequest().build();
    }
 */

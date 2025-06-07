package hse.kpo.services;

import hse.kpo.model.ReportMetadata;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import java.util.zip.GZIPOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ReportService {

    private final S3Client s3Client;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Value("${s3.compress}")
    private boolean compress;

    @Value("${s3.encrypt}")
    private boolean encrypt;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    private static final int PART_SIZE = 1_000_000; // 1MB

    public void saveReportAsync(String reportId, byte[] content, ReportMetadata metadata) {
        executor.submit(() -> {
            try {
                byte[] processed = preprocess(content);
                Map<String, String> metaMap = metadata.toMap();

                if (processed.length > PART_SIZE) {
                    log.info("Размер отчета > 1MB, выполняется шардирование (multipart upload)...");
                    multipartUpload(reportId, processed, metaMap);
                } else {
                    putSimpleObject(reportId, processed, metaMap);
                }

            } catch (Exception e) {
                log.error("Ошибка при сохранении отчета в S3: {}", e.getMessage(), e);
            }
        });
    }

    private byte[] preprocess(byte[] content) throws IOException {
        byte[] data = content;
        if (compress) {
            data = gzipCompress(data);
        }
        if (encrypt) {
            // TODO: заменить на реальное шифрование (например, AES-CBC)
            data = Base64.getEncoder().encode(data); // временное "шифрование"
        }
        return data;
    }

    private void putSimpleObject(String reportId, byte[] data, Map<String, String> metadata) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(reportId + ".json")
                .metadata(metadata)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(data));
        log.info("Отчет '{}' сохранён в S3 как простой объект", reportId);
    }

    private void multipartUpload(String reportId, byte[] data, Map<String, String> metadata) throws IOException {
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(reportId + ".json")
                .metadata(metadata)
                .build();

        CreateMultipartUploadResponse createResponse = s3Client.createMultipartUpload(createRequest);
        String uploadId = createResponse.uploadId();

        List<CompletedPart> completedParts = new ArrayList<>();

        int partNumber = 1;
        for (int offset = 0; offset < data.length; offset += PART_SIZE) {
            int end = Math.min(offset + PART_SIZE, data.length);
            byte[] partBytes = Arrays.copyOfRange(data, offset, end);

            UploadPartRequest uploadRequest = UploadPartRequest.builder()
                    .bucket(bucketName)
                    .key(reportId + ".json")
                    .uploadId(uploadId)
                    .partNumber(partNumber)
                    .build();

            UploadPartResponse uploadResponse = s3Client.uploadPart(uploadRequest, RequestBody.fromBytes(partBytes));

            completedParts.add(CompletedPart.builder()
                    .partNumber(partNumber)
                    .eTag(uploadResponse.eTag())
                    .build());

            partNumber++;
        }

        CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();

        CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(reportId + ".json")
                .uploadId(uploadId)
                .multipartUpload(completedUpload)
                .build();

        s3Client.completeMultipartUpload(completeRequest);
        log.info("Отчет '{}' успешно загружен в S3 через multipart upload", reportId);
    }

    private byte[] gzipCompress(byte[] input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(output)) {
            gzip.write(input);
        }
        return output.toByteArray();
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}

package hse.kpo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3BucketService {

    private final S3Client s3Client;

    /**
     * Создать новый bucket, если не существует.
     */
    public void createBucket(String bucketName) {
        if (bucketExists(bucketName)) {
            throw new IllegalStateException("Bucket '" + bucketName + "' уже существует");
        }

        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(request);
        log.info("Bucket '{}' успешно создан", bucketName);
    }

    /**
     * Проверить, существует ли bucket.
     */
    public boolean bucketExists(String bucketName) {
        try {
            HeadBucketRequest request = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.headBucket(request);
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

    /**
     * Получить список всех bucket-ов.
     */
    public List<String> listBuckets() {
        return s3Client.listBuckets()
                .buckets()
                .stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }

    /**
     * Удалить bucket, если пустой.
     */
    public void deleteBucket(String bucketName) {
        try {
            DeleteBucketRequest request = DeleteBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.deleteBucket(request);
            log.info("Bucket '{}' удалён", bucketName);
        } catch (S3Exception e) {
            log.error("Ошибка при удалении bucket '{}': {}", bucketName, e.awsErrorDetails().errorMessage());
            throw e;
        }
    }
}

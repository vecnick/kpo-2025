package hse.kpo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

@Component
@RequiredArgsConstructor
public class S3BucketInitializer implements CommandLineRunner {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Проверяем существование бакета
            s3Client.headBucket(HeadBucketRequest.builder()
                .bucket(bucketName)
                .build());
            System.out.println("Bucket already exists: " + bucketName);
        } catch (Exception e) {
            // Создаем бакет если не существует
            s3Client.createBucket(CreateBucketRequest.builder()
                .bucket(bucketName)
                .build());
            System.out.println("Bucket created: " + bucketName);
        }
    }
}
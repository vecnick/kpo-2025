package hse.kpo.grpc;

import hse.kpo.facade.Hse;
import hse.kpo.model.ReportMetadata;
import hse.kpo.services.S3ReportService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@GrpcService
public class ReportGrpcController extends ReportServiceGrpc.ReportServiceImplBase {

    private final Hse hse;
    private final S3ReportService s3ReportService;

    @Override
    public void getLatestReport(ReportRequest request,
                                StreamObserver<ReportResponse> responseObserver) {
        String report = hse.generateReport(); // получаем отчёт

        // ⬇️ Сохраняем в S3
        ReportMetadata metadata = new ReportMetadata();
        metadata.setReportName("Sales Report");
        metadata.setServiceSource("notifications");
        metadata.setReportType("sales");
        metadata.setSentAt(LocalDateTime.now());

        byte[] content = report.getBytes(StandardCharsets.UTF_8);
        String reportId = UUID.randomUUID().toString();

        s3ReportService.saveReportAsync(reportId, content, metadata);

        // ⬇️ Возвращаем по gRPC
        ReportResponse response = ReportResponse.newBuilder()
                .setTitle("Sales Report")
                .setContent(report)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

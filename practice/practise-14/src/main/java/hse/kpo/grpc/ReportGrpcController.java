package hse.kpo.grpc;

import hse.kpo.facade.Hse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class ReportGrpcController extends ReportServiceGrpc.ReportServiceImplBase {

    private final Hse hse;

    @Override
    public void getLatestReport(ReportRequest request,
                                StreamObserver<ReportResponse> responseObserver) {
        String report = hse.generateReport();
        ReportResponse response = ReportResponse.newBuilder()
            .setTitle("Sales Report")
            .setContent(report)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
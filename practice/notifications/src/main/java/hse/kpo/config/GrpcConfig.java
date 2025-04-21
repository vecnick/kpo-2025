package hse.kpo.config;

import hse.kpo.grpc.ReportServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @GrpcClient("report-service")
    private ReportServiceGrpc.ReportServiceBlockingStub reportServiceStub;

    @Bean
    public ReportServiceGrpc.ReportServiceBlockingStub reportServiceStub() {
        return reportServiceStub;
    }
}

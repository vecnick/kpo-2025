package analysis.grpc;

import analysis.interfaces.IStoringGrpcImpl;
import io.grpc.stub.StreamObserver;
import io.swagger.v3.oas.annotations.servers.Server;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import storing.FileInfoOuterClass;
import storing.StoringServiceGrpc;

import java.util.List;
import java.util.Optional;

@Service
public class StoringGrpcImpl implements IStoringGrpcImpl {

    @GrpcClient("file_storing_service")
    private StoringServiceGrpc.StoringServiceBlockingStub storingServiceBlockingStub;

    @Override
    public Optional<List<Integer>> getAllIds() {
        try {
            FileInfoOuterClass.IntegerListResponse integerListResponse = storingServiceBlockingStub.getAllIds(makeEmptyRequest());
            return Optional.of(integerListResponse.getIdsList());
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getAllIds.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getHashById(int id) {
        try {
            FileInfoOuterClass.HashResponse hashResponse = storingServiceBlockingStub.getHashById(makeIdRequest(id));
            if (hashResponse.getFound()) {
                return Optional.of(hashResponse.getHash());
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getHashById.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getFileTextById(int id) {
        try {
            FileInfoOuterClass.TextResponse textResponse = storingServiceBlockingStub.getFileTextById(makeIdRequest(id));
            if (textResponse.getFound()) {
                return Optional.of(textResponse.getText());
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getFileTextById.");
            return Optional.empty();
        }
    }

    private FileInfoOuterClass.EmptyRequest makeEmptyRequest() {
        return FileInfoOuterClass.EmptyRequest.newBuilder().build();
    }

    private FileInfoOuterClass.IdRequest makeIdRequest(int id) {
        return FileInfoOuterClass.IdRequest.newBuilder()
                .setId(id)
                .build();
    }
}

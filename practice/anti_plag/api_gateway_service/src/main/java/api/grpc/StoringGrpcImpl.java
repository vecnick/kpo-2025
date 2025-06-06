package api.grpc;

import api.interfaces.IStoringGrpcImpl;
import api.record.FileInfoParams;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import storing.FileInfoOuterClass;
import storing.StoringServiceGrpc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoringGrpcImpl implements IStoringGrpcImpl {

    @GrpcClient("file_storing_service")
    private StoringServiceGrpc.StoringServiceBlockingStub storingServiceBlockingStub;

    @Override
    public Optional<List<FileInfoParams>> getAll() {
        try {
            FileInfoOuterClass.FileInfoListResponse fileInfoListResponse = storingServiceBlockingStub.getAll(makeEmptyRequest());
            List<FileInfoParams> fileInfoParamsList = fileInfoListResponse.getFileInfoList().stream()
                    .map(this::makeFileInfoParams)
                    .collect(Collectors.toList());
            return Optional.of(fileInfoParamsList);
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getAll.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<FileInfoParams> getById(int id) {
        try {
            FileInfoOuterClass.FileInfoResponse fileInfoResponse = storingServiceBlockingStub.getById(makeIdRequest(id));
            if (fileInfoResponse.getFound()) {
                return Optional.of(makeFileInfoParams(fileInfoResponse.getFileInfo()));
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getById.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getLocationById(int id) {
        try {
            FileInfoOuterClass.LocationResponse locationResponse = storingServiceBlockingStub.getLocationById(makeIdRequest(id));
            if (locationResponse.getFound()) {
                return Optional.of(locationResponse.getLocation());
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис FileInfoService аварийно завершился на вызове функции getLocationById.");
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

    @Override
    public Optional<Integer> saveFileInfo(String location, String text) {
        try {
            FileInfoOuterClass.IdResponse idResponse = storingServiceBlockingStub.saveFileInfo(makeMultipartFileRequest(location, text));
            if (idResponse.getFound()) {
                return Optional.of(idResponse.getId());
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис FileService аварийно завершился на вызове функции saveFileInfo.");
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteFileInfoById(int id) {
        try {
            FileInfoOuterClass.BoolResponse boolResponse = storingServiceBlockingStub.deleteFileInfoById(makeIdRequest(id));
            return boolResponse.getResult();
        } catch (Exception e) {
            System.out.println("Сервис FileService аварийно завершился на вызове функции deleteFileInfoById.");
            return false;
        }
    }

    private FileInfoParams makeFileInfoParams(FileInfoOuterClass.FileInfoParam fileInfoParam) {
        return FileInfoParams.builder()
                .id(fileInfoParam.getId())
                .name(fileInfoParam.getName())
                .hash(fileInfoParam.getHash())
                .location(fileInfoParam.getLocation())
                .build();
    }

    private FileInfoOuterClass.EmptyRequest makeEmptyRequest() {
        return FileInfoOuterClass.EmptyRequest.newBuilder().build();
    }

    private FileInfoOuterClass.IdRequest makeIdRequest(int id) {
        return FileInfoOuterClass.IdRequest.newBuilder()
                .setId(id)
                .build();
    }

    private FileInfoOuterClass.MultipartFileRequest makeMultipartFileRequest(String location, String text) {
        return FileInfoOuterClass.MultipartFileRequest.newBuilder()
                .setLocation(location)
                .setText(text)
                .build();
    }
}
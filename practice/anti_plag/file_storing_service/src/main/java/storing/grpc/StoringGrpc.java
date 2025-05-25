package storing.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import storing.FileInfoOuterClass;
import storing.StoringServiceGrpc;
import storing.entity.FileInfo;
import storing.interfaces.IFileInfoService;
import storing.interfaces.IFileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class StoringGrpc extends StoringServiceGrpc.StoringServiceImplBase {

    private final IFileInfoService fileInfoService;
    private final IFileService fileService;

    public StoringGrpc(IFileInfoService fileInfoService, IFileService fileService) {
        this.fileInfoService = fileInfoService;
        this.fileService = fileService;
    }

    @Override
    public void getAll(FileInfoOuterClass.EmptyRequest request, StreamObserver<FileInfoOuterClass.FileInfoListResponse> responseObserver) {
        List<FileInfo> filesInfo = fileInfoService.getAll();
        responseObserver.onNext(makeFileInfoListResponse(filesInfo));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllIds(FileInfoOuterClass.EmptyRequest request, StreamObserver<FileInfoOuterClass.IntegerListResponse> responseObserver) {
        List<Integer> ids = fileInfoService.getAllIds();
        responseObserver.onNext(makeIntegerListResponse(ids));
        responseObserver.onCompleted();
    }

    @Override
    public void getById(FileInfoOuterClass.IdRequest request, StreamObserver<FileInfoOuterClass.FileInfoResponse> responseObserver) {
        Optional<FileInfo> fileInfo = fileInfoService.getById(request.getId());
        responseObserver.onNext(makeFileInfoResponse(fileInfo));
        responseObserver.onCompleted();
    }

    @Override
    public void getIdByHash(FileInfoOuterClass.HashRequest request, StreamObserver<FileInfoOuterClass.IdResponse> responseObserver) {
        Optional<Integer> id = fileInfoService.getIdByHash(request.getHash());
        responseObserver.onNext(makeIdResponse(id));
        responseObserver.onCompleted();
    }

    @Override
    public void getHashById(FileInfoOuterClass.IdRequest request, StreamObserver<FileInfoOuterClass.HashResponse> responseObserver) {
        Optional<String> hash = fileInfoService.getHashById(request.getId());
        responseObserver.onNext(makeHashResponse(hash));
        responseObserver.onCompleted();
    }

    @Override
    public void getLocationById(FileInfoOuterClass.IdRequest request, StreamObserver<FileInfoOuterClass.LocationResponse> responseObserver) {
        Optional<String> location = fileInfoService.getLocationById(request.getId());
        responseObserver.onNext(makeLocationResponse(location));
        responseObserver.onCompleted();
    }

    @Override
    public void getFileTextById(FileInfoOuterClass.IdRequest request, StreamObserver<FileInfoOuterClass.TextResponse> responseObserver) {
        Optional<String> text = fileInfoService.getFileTextById(request.getId());
        responseObserver.onNext(makeTextResponse(text));
        responseObserver.onCompleted();
    }

    @Override
    public void saveFileInfo(FileInfoOuterClass.MultipartFileRequest request, StreamObserver<FileInfoOuterClass.IdResponse> responseObserver) {
        Optional<Integer> id = fileService.save(request.getLocation(), request.getText());
        responseObserver.onNext(makeIdResponse(id));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteFileInfoById(FileInfoOuterClass.IdRequest request, StreamObserver<FileInfoOuterClass.BoolResponse> responseObserver) {
        boolean wasDeleted = fileService.deleteById(request.getId());
        responseObserver.onNext(makeBoolResponse(wasDeleted));
        responseObserver.onCompleted();
    }

    private FileInfoOuterClass.FileInfoParam makeFileInfoParam(FileInfo fileInfo) {
        return FileInfoOuterClass.FileInfoParam.newBuilder()
                .setId(fileInfo.getId())
                .setName(fileInfo.getName())
                .setHash(fileInfo.getHash())
                .setLocation(fileInfo.getLocation())
                .build();
    }

    private FileInfoOuterClass.FileInfoListResponse makeFileInfoListResponse(List<FileInfo> filesInfo) {
        List<FileInfoOuterClass.FileInfoParam> filesInfoParam = filesInfo.stream()
                .map(this::makeFileInfoParam)
                .collect(Collectors.toList());

        return FileInfoOuterClass.FileInfoListResponse.newBuilder()
                .addAllFileInfo(filesInfoParam)
                .build();
    }

    private FileInfoOuterClass.IntegerListResponse makeIntegerListResponse(List<Integer> ids) {
        return FileInfoOuterClass.IntegerListResponse.newBuilder()
                .addAllIds(ids)
                .build();
    }

    private FileInfoOuterClass.FileInfoResponse makeFileInfoResponse(Optional<FileInfo> fileInfo) {
        FileInfoOuterClass.FileInfoResponse.Builder builder = FileInfoOuterClass.FileInfoResponse.newBuilder();
        if (fileInfo.isPresent()) { builder.setFileInfo(makeFileInfoParam(fileInfo.get())); }
        builder.setFound(fileInfo.isPresent());
        return builder.build();
    }

    private FileInfoOuterClass.IdResponse makeIdResponse(Optional<Integer> id) {
        FileInfoOuterClass.IdResponse.Builder builder = FileInfoOuterClass.IdResponse.newBuilder();
        if (id.isPresent()) { builder.setId(id.get()); }
        builder.setFound(id.isPresent());
        return builder.build();
    }

    private FileInfoOuterClass.HashResponse makeHashResponse(Optional<String> hash) {
        FileInfoOuterClass.HashResponse.Builder builder = FileInfoOuterClass.HashResponse.newBuilder();
        if (hash.isPresent()) { builder.setHash(hash.get()); }
        builder.setFound(hash.isPresent());
        return builder.build();
    }

    private FileInfoOuterClass.LocationResponse makeLocationResponse(Optional<String> location) {
        FileInfoOuterClass.LocationResponse.Builder builder = FileInfoOuterClass.LocationResponse.newBuilder();
        if (location.isPresent()) { builder.setLocation(location.get()); }
        builder.setFound(location.isPresent());
        return builder.build();
    }

    private FileInfoOuterClass.TextResponse makeTextResponse(Optional<String> text) {
        FileInfoOuterClass.TextResponse.Builder builder = FileInfoOuterClass.TextResponse.newBuilder();
        if (text.isPresent()) { builder.setText(text.get()); }
        builder.setFound(text.isPresent());
        return builder.build();
    }

    private FileInfoOuterClass.BoolResponse makeBoolResponse(boolean result) {
        return FileInfoOuterClass.BoolResponse.newBuilder()
                .setResult(result)
                .build();
    }
}

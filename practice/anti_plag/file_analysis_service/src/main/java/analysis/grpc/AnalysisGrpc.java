package analysis.grpc;

import analysis.AnalysisServiceGrpc;
import analysis.TextAnalysOuterClass;
import analysis.entity.TextAnalys;
import analysis.interfaces.IAnalysService;
import analysis.interfaces.ITextAnalysService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@GrpcService
public class AnalysisGrpc extends AnalysisServiceGrpc.AnalysisServiceImplBase {

    private final ITextAnalysService textAnalysService;
    private final IAnalysService analysService;

    public AnalysisGrpc(ITextAnalysService textAnalysService, IAnalysService analysService) {
        this.textAnalysService = textAnalysService;
        this.analysService = analysService;
    }

    @Override
    public void getAll(TextAnalysOuterClass.EmptyRequest request, StreamObserver<TextAnalysOuterClass.TextAnalysListResponse> responseObserver) {
        List<TextAnalys> textsAnalys = textAnalysService.getAll();
        responseObserver.onNext(makeTextAnalysListResponse(textsAnalys));
        responseObserver.onCompleted();
    }

    @Override
    public void getByFileId(TextAnalysOuterClass.IdRequest request, StreamObserver<TextAnalysOuterClass.TextAnalysResponse> responseObserver) {
        Optional<TextAnalys> textAnalys = textAnalysService.getByFileId(request.getId());
        responseObserver.onNext(makeTextAnalysResponse(textAnalys));
        responseObserver.onCompleted();
    }

    @Override
    public void getPathByFileId(TextAnalysOuterClass.IdRequest request, StreamObserver<TextAnalysOuterClass.PathResponse> responseObserver) {
        Optional<String> path = textAnalysService.getPathByFileId(request.getId());
        responseObserver.onNext(makePathResponse(path));
        responseObserver.onCompleted();
    }

    @Override
    public void startAnalys(TextAnalysOuterClass.IdRequest request, StreamObserver<TextAnalysOuterClass.TextAnalysResponse> responseObserver) {
        Optional<TextAnalys> textAnalys = analysService.startAnalys(request.getId());
        responseObserver.onNext(makeTextAnalysResponse(textAnalys));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteAnalys(TextAnalysOuterClass.IdRequest request, StreamObserver<TextAnalysOuterClass.BoolResponse> responseObserver) {
        boolean wasDeleted = analysService.deleteAnalys(request.getId());
        responseObserver.onNext(makeBoolResponse(wasDeleted));
        responseObserver.onCompleted();
    }


    private TextAnalysOuterClass.TextAnalysParam makeTextAnalysParam(TextAnalys textAnalys) {
        return TextAnalysOuterClass.TextAnalysParam.newBuilder()
                .setId(textAnalys.getId())
                .setParagraphs(textAnalys.getParagraphs())
                .setWords(textAnalys.getWords())
                .setSymbols(textAnalys.getSymbols())
                .setPlagiatePoints(textAnalys.getPlagiatePoints())
                .setWCPicName(textAnalys.getWCPicName())
                .setWCPicPath(textAnalys.getWCPicPath())
                .setFileId(textAnalys.getFileId())
                .build();
    }

    private TextAnalysOuterClass.TextAnalysListResponse makeTextAnalysListResponse(List<TextAnalys> textsAnalys) {
        List<TextAnalysOuterClass.TextAnalysParam> textsInfoParam = textsAnalys.stream()
                .map(this::makeTextAnalysParam)
                .collect(Collectors.toList());

        return TextAnalysOuterClass.TextAnalysListResponse.newBuilder()
                .addAllTextAnalys(textsInfoParam)
                .build();
    }

    private TextAnalysOuterClass.TextAnalysResponse makeTextAnalysResponse(Optional<TextAnalys> textAnalys) {
        TextAnalysOuterClass.TextAnalysResponse.Builder builder = TextAnalysOuterClass.TextAnalysResponse.newBuilder();
        if (textAnalys.isPresent()) { builder.setTextAnalys(makeTextAnalysParam(textAnalys.get())); }
        builder.setFound(textAnalys.isPresent());
        return builder.build();
    }

    private TextAnalysOuterClass.PathResponse makePathResponse(Optional<String> path) {
        TextAnalysOuterClass.PathResponse.Builder builder = TextAnalysOuterClass.PathResponse.newBuilder();
        if (path.isPresent()) { builder.setPath(path.get()); }
        builder.setFound(path.isPresent());
        return builder.build();
    }

    private TextAnalysOuterClass.BoolResponse makeBoolResponse(boolean result) {
        return TextAnalysOuterClass.BoolResponse.newBuilder()
                .setResult(result)
                .build();
    }
}
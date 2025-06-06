package api.grpc;

import analysis.AnalysisServiceGrpc;
import analysis.TextAnalysOuterClass;
import api.interfaces.IAnalysisGrpcImpl;
import api.record.TextAnalysParams;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import storing.FileInfoOuterClass;
import storing.StoringServiceGrpc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalysisGrpcImpl implements IAnalysisGrpcImpl {

    @GrpcClient("file_analysis_service")
    private AnalysisServiceGrpc.AnalysisServiceBlockingStub analysisServiceBlockingStub;

    @Override
    public Optional<List<TextAnalysParams>> getAll() {
        try {
            TextAnalysOuterClass.TextAnalysListResponse textAnalysListResponse = analysisServiceBlockingStub.getAll(makeEmptyRequest());
            List<TextAnalysParams> textAnalysParamsList = textAnalysListResponse.getTextAnalysList().stream()
                    .map(this::makeTextAnalysParams)
                    .collect(Collectors.toList());
            return Optional.of(textAnalysParamsList);
        } catch (Exception e) {
            System.out.println("Сервис TextAnalysService аварийно завершился на вызове функции getAll.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TextAnalysParams> getByFileId(int fileId) {
        try {
            TextAnalysOuterClass.TextAnalysResponse textAnalysResponse = analysisServiceBlockingStub.getByFileId(makeIdRequest(fileId));
            if (textAnalysResponse.getFound()) {
                return Optional.of(makeTextAnalysParams(textAnalysResponse.getTextAnalys()));
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис TextAnalysService аварийно завершился на вызове функции getByFileId.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getPathByFileId(int fileId) {
        try {
            TextAnalysOuterClass.PathResponse pathResponse = analysisServiceBlockingStub.getPathByFileId(makeIdRequest(fileId));
            if (pathResponse.getFound()) {
                return Optional.of(pathResponse.getPath());
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис TextAnalysService аварийно завершился на вызове функции getPathByFileId.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<byte[]> getWordCloudPic(int fileId) {
        try {
            TextAnalysOuterClass.PicResponse picResponse = analysisServiceBlockingStub.getWordCloudPic(makeIdRequest(fileId));
            if (picResponse.getFound()) {
                return Optional.of(picResponse.getPic().toByteArray());
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис TextAnalysService аварийно завершился на вызове функции getWordCloudPic.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TextAnalysParams> startAnalys(int fileId) {
        try {
            TextAnalysOuterClass.TextAnalysResponse textAnalysResponse = analysisServiceBlockingStub.startAnalys(makeIdRequest(fileId));
            if (textAnalysResponse.getFound()) {
                return Optional.of(makeTextAnalysParams(textAnalysResponse.getTextAnalys()));
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Сервис AnalysService аварийно завершился на вызове функции startAnalys.");
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteAnalys(int fileId) {
        try {
            TextAnalysOuterClass.BoolResponse boolResponse = analysisServiceBlockingStub.deleteAnalys(makeIdRequest(fileId));
            return boolResponse.getResult();
        } catch (Exception e) {
            System.out.println("Сервис AnalysService аварийно завершился на вызове функции deleteAnalys.");
            return false;
        }
    }

    private TextAnalysParams makeTextAnalysParams(TextAnalysOuterClass.TextAnalysParam textAnalysParam) {
        return TextAnalysParams.builder()
                .id(textAnalysParam.getId())
                .paragraphs(textAnalysParam.getParagraphs())
                .words(textAnalysParam.getWords())
                .symbols(textAnalysParam.getSymbols())
                .plagiatePoints(textAnalysParam.getPlagiatePoints())
                .WCPicName(textAnalysParam.getWCPicName())
                .WCPicPath(textAnalysParam.getWCPicPath())
                .fileId(textAnalysParam.getFileId())
                .build();
    }

    private TextAnalysOuterClass.EmptyRequest makeEmptyRequest() {
        return TextAnalysOuterClass.EmptyRequest.newBuilder().build();
    }

    private TextAnalysOuterClass.IdRequest makeIdRequest(int id) {
        return TextAnalysOuterClass.IdRequest.newBuilder()
                .setId(id)
                .build();
    }
}

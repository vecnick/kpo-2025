package api.interfaces;

import analysis.TextAnalysOuterClass;
import api.record.TextAnalysParams;

import java.util.List;
import java.util.Optional;

public interface IAnalysisGrpcImpl {

    Optional<List<TextAnalysParams>> getAll();
    Optional<TextAnalysParams> getByFileId(int fileId);
    Optional<String> getPathByFileId(int fileId);
    Optional<byte[]> getWordCloudPic(int fileId);

    Optional<TextAnalysParams> startAnalys(int fileId);
    boolean deleteAnalys(int fileId);
}

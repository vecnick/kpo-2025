package analysis.interfaces;

import analysis.record.WordCloudPicParams;

import java.util.Optional;

public interface IWordCloudService {

    Optional<WordCloudPicParams> createWordCloud(int fileId);
    boolean deleteWordCloud(String filePath);
}

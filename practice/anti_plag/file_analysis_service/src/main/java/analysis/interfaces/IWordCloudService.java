package analysis.interfaces;

import analysis.record.WordCloudPicParams;

import java.io.IOException;
import java.util.Optional;

public interface IWordCloudService {

    Optional<WordCloudPicParams> getWordCloud(int id);
}

package analysis.interfaces;

import analysis.record.WordCloudPicParams;

import java.io.IOException;

public interface IWordCloudService {

    WordCloudPicParams getWordCloud(String text) throws IOException, InterruptedException;
}

package analysis.record;

import analysis.entity.TextAnalys;
import lombok.Builder;

@Builder
public record TextAnalysParams(
        int paragraphs,
        int words,
        int symbols,
        int plagiatePoints,
        String WCPicName,
        String WCPicPath,
        int fileId
) {
    public TextAnalysParams(TextAnalys textAnalys) {
        this(textAnalys.getParagraphs(),
                textAnalys.getWords(),
                textAnalys.getSymbols(),
                textAnalys.getPlagiatePoints(),
                textAnalys.getWCPicName(),
                textAnalys.getWCPicPath(),
                textAnalys.getFileId());
    }
}

package api.record;

import lombok.Builder;

@Builder
public record TextAnalysParams(
        int id,
        int paragraphs,
        int words,
        int symbols,
        int plagiatePoints,
        String WCPicName,
        String WCPicPath,
        int fileId
) {
}

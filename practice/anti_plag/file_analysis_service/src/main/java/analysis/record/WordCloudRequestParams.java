package analysis.record;

import lombok.Builder;

@Builder
public record WordCloudRequestParams(
        String format,
        int width,
        int height,
        String fontFamily,
        int fontScale,
        String scale,
        String backgroundColor,
        String text
) {
}
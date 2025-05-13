package storing.record;

import lombok.Builder;
import storing.entity.FileInfo;

@Builder
public record FileInfoParams(
        String name,
        String hash,
        String location
) {

    public FileInfoParams(FileInfo fileInfo) {
        this(fileInfo.getName(),
            fileInfo.getHash(),
            fileInfo.getLocation());
    }
}

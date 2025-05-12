package storing.record;

import storing.entity.FileInfo;

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

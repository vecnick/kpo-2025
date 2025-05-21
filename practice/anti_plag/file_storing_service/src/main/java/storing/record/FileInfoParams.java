package storing.record;

import lombok.Builder;

@Builder
public record FileInfoParams(
        int id,
        String name,
        String hash,
        String location
) {
}

package hse.kpo.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class ReportMetadata {
    private String reportName;
    private String serviceSource;
    private String reportType;
    private LocalDateTime sentAt;

    public Map<String, String> toMap() {
        Map<String, String> meta = new HashMap<>();
        meta.put("name", reportName);
        meta.put("source", serviceSource);
        meta.put("type", reportType);
        meta.put("sentAt", sentAt.toString());
        return meta;
    }
}

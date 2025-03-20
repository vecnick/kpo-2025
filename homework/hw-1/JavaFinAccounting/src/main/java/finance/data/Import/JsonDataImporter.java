package finance.data.Import;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataImporter extends DataImporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected List<?> parseData(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            throw new RuntimeException("JSON parsing error ", e);
        }
    }
}
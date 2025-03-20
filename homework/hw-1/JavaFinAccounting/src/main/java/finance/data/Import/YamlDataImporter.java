package finance.data.Import;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlDataImporter extends DataImporter {
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @Override
    protected List<?> parseData(String data) {
        List<Map<String, Object>> result = new ArrayList<>();

        String[] yamlDocuments = data.split("\n");

        for (String yamlDoc : yamlDocuments) {
            try {
                Map<String, Object> parsedData = objectMapper.readValue(yamlDoc, new TypeReference<>() {});
                result.add(parsedData);
            } catch (IOException e) {
                throw new RuntimeException("YAML parsing error", e);
            }
        }

        return result;
    }
}
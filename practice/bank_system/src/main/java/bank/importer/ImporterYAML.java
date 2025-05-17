package bank.importer;

import bank.enums.DomainType;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Component
public class ImporterYAML<T>  implements ImporterStrategy<T> {
    private final YAMLMapper yamlMapper = new YAMLMapper();

    @Override
    public Report<T> parse(Class<T> clazz, BufferedReader bufferedReader) throws IOException {
        return yamlMapper.readValue(bufferedReader,
                yamlMapper.getTypeFactory().constructParametricType(Report.class, clazz));
    }
}

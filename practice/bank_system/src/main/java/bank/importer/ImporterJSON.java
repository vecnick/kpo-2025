package bank.importer;

import bank.enums.DomainType;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class ImporterJSON<T>  implements ImporterStrategy<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Report<T> parse(Class<T> clazz, BufferedReader bufferedReader) throws IOException {
        return objectMapper.readValue(bufferedReader,
                objectMapper.getTypeFactory().constructParametricType(Report.class, clazz));
    }

}
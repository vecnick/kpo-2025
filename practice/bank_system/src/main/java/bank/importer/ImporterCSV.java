package bank.importer;

import bank.enums.DomainType;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Component
public class ImporterCSV<T>  implements ImporterStrategy<T> {
    private final CsvMapper csvMapper = new CsvMapper();

    @Override
    public Report<T> parse(Class<T> clazz, BufferedReader bufferedReader) throws IOException {
        DomainType title = DomainType.valueOf(bufferedReader.readLine());

        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader().withColumnSeparator(',');

        MappingIterator<T> iterator = csvMapper.readerFor(clazz).with(schema).readValues(bufferedReader);
        List<T> content = iterator.readAll();

        return new Report<>(title, content);
    }

}

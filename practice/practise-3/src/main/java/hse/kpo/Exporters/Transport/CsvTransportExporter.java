package hse.kpo.Exporters.Transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.interfaces.Transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Stream;

public class CsvTransportExporter implements TransportExporter {
    @Override
    public void export(List<Transport> transports, Writer writer) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();

        for (Transport transport : transports) {
            writer.write(String.format("%d,%s,%s\n",
                    transport.getVin(),
                    transport.getTransportType(),
                    transport.getEngineType()));
        }
    }
}

package hse.kpo.export.transport.impl;

import hse.kpo.export.transport.TransportExporter;
import hse.kpo.interfaces.Transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvTransportExporter implements TransportExporter {

    public void export(List<Transport> transports, Writer writer) throws IOException {
        // Заголовок CSV
        writer.write("VIN,Type,EngineType\n");

        // Данные
        for (Transport transport : transports) {

            String line = String.format("%d,%s,%s\n",
                    transport.getVin(),
                    transport.getTransportType(),
                    transport.getEngineType());

            writer.write(line);
        }
        writer.flush();
    }
}
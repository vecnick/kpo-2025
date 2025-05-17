package hse.kpo.export.transport.impl;

import hse.kpo.export.transport.TransportExporter;
import hse.kpo.interfaces.Transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XmlTransportExporter implements TransportExporter {

    public void export(List<Transport> transports, Writer writer) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<TransportList>\n");

        // Данные
        for (Transport transport : transports) {
            writer.write(String.format("""
                              <Vehicle>
                                  <VIN>%d</VIN>
                                  <Type>%s</Type>
                                  <Engine>
                                      <Type>%s</Type>
                                  </Engine>
                              </Vehicle>
                            """,
                    transport.getVin(),
                    transport.getTransportType(),
                    transport.getEngineType()
            ));
        }

        writer.write("</TransportList>");
        writer.flush();
    }
}
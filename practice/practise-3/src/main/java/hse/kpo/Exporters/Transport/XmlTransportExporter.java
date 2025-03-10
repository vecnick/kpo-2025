package hse.kpo.Exporters.Transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.kpo.interfaces.Transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XmlTransportExporter implements TransportExporter {
    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void export(List<Transport> transports, Writer writer) throws IOException {
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
    }
}

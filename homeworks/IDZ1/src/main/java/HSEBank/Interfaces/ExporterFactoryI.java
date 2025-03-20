package HSEBank.Interfaces;

import HSEBank.Importers.ServiceImport.AbstractServiceImporter;

public interface ExporterFactoryI {
    ExporterI createExporter(String fileName);
}

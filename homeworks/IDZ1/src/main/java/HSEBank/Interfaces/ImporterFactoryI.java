package HSEBank.Interfaces;

import HSEBank.Importers.ServiceImport.AbstractServiceImporter;

public interface ImporterFactoryI {
    AbstractServiceImporter createImporter(String fileName);
}

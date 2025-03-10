package hse.kpo.Importers.TransportImporter;

import hse.kpo.services.CarServiceI;
import hse.kpo.services.ShipService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public interface TransportImporter {
    void importCar(CarServiceI carService, ShipService shipService, BufferedReader reader) throws IOException;
}
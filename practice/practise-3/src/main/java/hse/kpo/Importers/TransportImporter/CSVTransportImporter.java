package hse.kpo.Importers.TransportImporter;

import hse.kpo.Enums.FactoryByName;
import hse.kpo.Enums.TransportReportFormat;
import hse.kpo.domains.params.EmptyEngineParams;
import hse.kpo.domains.params.PedalEngineParams;
import hse.kpo.factories.Cars.HandCarFactoryI;
import hse.kpo.factories.Cars.PedalCarFactoryI;
import hse.kpo.factories.Ships.ShipFactory;
import hse.kpo.services.CarServiceI;
import hse.kpo.services.ShipService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.runtime.reflect.Factory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

import hse.kpo.Enums.Types;

import static hse.kpo.Enums.Types.CAR;

@Component
public class CSVTransportImporter implements TransportImporter {

    @Override
    public void importCar(CarServiceI carService, ShipService shipService, BufferedReader reader) throws IOException {
        var nextStr = "";
        while ((nextStr = reader.readLine()) != null) {
            String[] values = nextStr.split(",");
            if (Objects.equals(values[0], "CAR")) {
                FactoryByName type = FactoryByName.valueOf(values[1]);
                var factory = FactoryByName.getFactory(type);
                if (factory.getClass() == PedalCarFactoryI.class) {
                    carService.addCar(factory, new PedalEngineParams(Integer.parseInt(values[2])));
                } else {
                    carService.addCar(factory, new EmptyEngineParams());
                }
            } else {
                var factory = new ShipFactory();
                shipService.addCar(factory, new EmptyEngineParams());
            }

        }
    }
}

package zooapi.zooerp2.Domain.Factories;

import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Enclosure;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.ValueObjects.AnimalNumber;
import zooapi.zooerp2.Domain.ValueObjects.Size3D;

import java.util.UUID;

@Component
public class EnclosureFactory {
    int lastId = 0;

    public Enclosure createEnclosureFactory(AnimalType animalType, Size3D size3D, AnimalNumber maxAnimalNumber) {
        return new Enclosure(++lastId, animalType, size3D, maxAnimalNumber);
    }
}

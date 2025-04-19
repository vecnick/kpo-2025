package zooapi.zooerp2.Domain.Interfaces.Application;

import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Entities.Enclosure;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.ValueObjects.AnimalNumber;
import zooapi.zooerp2.Domain.ValueObjects.Size3D;

import java.util.Optional;
import java.util.UUID;

public interface EnclosureServiceI {
    Optional<Enclosure> addEnclosure(AnimalType animalType, Size3D size3D, AnimalNumber maxAnimalNumber);
    void deleteEnclosure(int id);
    Optional<Enclosure> getEnclosure(int animalId);
}

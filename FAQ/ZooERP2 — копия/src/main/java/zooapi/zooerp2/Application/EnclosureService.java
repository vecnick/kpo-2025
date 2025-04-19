package zooapi.zooerp2.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Enclosure;
import zooapi.zooerp2.Domain.Enums.AnimalType;
import zooapi.zooerp2.Domain.Factories.EnclosureFactory;
import zooapi.zooerp2.Domain.Interfaces.Application.EnclosureServiceI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.EnclosureRepositoryI;
import zooapi.zooerp2.Domain.ValueObjects.AnimalNumber;
import zooapi.zooerp2.Domain.ValueObjects.Size3D;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EnclosureService implements EnclosureServiceI {
    private final EnclosureRepositoryI enclosureRepository;
    private final EnclosureFactory enclosureFactory;

    @Override
    public Optional<Enclosure> addEnclosure(AnimalType animalType, Size3D size3D, AnimalNumber maxAnimalNumber) {
        var newEnclosure = enclosureFactory.createEnclosureFactory(animalType, size3D, maxAnimalNumber);
        return enclosureRepository.addEnclosure(newEnclosure);
    }

    @Override
    public void deleteEnclosure(int id) {
        enclosureRepository.deleteEnclosure(id);
    }

    @Override
    public Optional<Enclosure> getEnclosure(int animalId) {
        return enclosureRepository.getEnclosure(animalId);
    }
}

package zooapi.zooerp2.Infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.Animal;
import zooapi.zooerp2.Domain.Entities.Enclosure;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.EnclosureRepositoryI;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EnclosureRepository implements EnclosureRepositoryI {
    ArrayList<Enclosure> enclosures = new ArrayList<>();

    @Override
    public Optional<Enclosure> addEnclosure(Enclosure e) {
        if (!enclosures.contains(e)) {
            enclosures.add(e);
            return Optional.of(e);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteEnclosure(int animalId) {
        var t = enclosures.stream().filter(enclosure -> enclosure.getId() == animalId).findFirst();
        t.ifPresent(animal -> enclosures.remove(animal));
    }


    @Override
    public Optional<Enclosure> getEnclosure(int animalId) {
        return enclosures.stream().filter(enclosure -> enclosure.getId() == animalId).findFirst();
    }

    @Override
    public int getEnclosureCount() {
        return enclosures.size();
    }

    @Override
    public int getEnclosureCapacity() {
        int[] sum = {0};
        enclosures.stream().forEach(enclosure -> {sum[0] += enclosure.getMaxAnimalNumber().number();});
        return sum[0];
    }

    @Override
    public Optional<Enclosure> getEnclosureByAnimalId(int animalId) {
        return enclosures.stream().filter(enclosure -> enclosure.checkAnimalInside(animalId).isPresent()).findFirst();
    }
}

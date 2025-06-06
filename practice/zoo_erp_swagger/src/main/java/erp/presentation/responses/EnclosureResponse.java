package erp.presentation.responses;

import erp.domain.enums.Cleanness;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IAnimalType;
import erp.domain.interfaces.IEnclosure;

import java.util.List;
import java.util.stream.Collectors;

public record EnclosureResponse(
    int id,
    AnimalTypeResponse allowedAnimalType,
    int size,
    int animalsCount,
    int maxAnimalsCount,
    Cleanness cleanness,
    List<AnimalResponse> animals
) {
    public EnclosureResponse(IEnclosure enclosure) {
        this(enclosure.getId(),
            new AnimalTypeResponse(enclosure.getAllowedAnimalType()),
            enclosure.getSize(),
            enclosure.getAnimalsCount(),
            enclosure.getMaxAnimalsCount(),
            enclosure.getCleanness(),
            enclosure.getAnimals().stream().map(AnimalResponse::new).collect(Collectors.toList()));
    }
}

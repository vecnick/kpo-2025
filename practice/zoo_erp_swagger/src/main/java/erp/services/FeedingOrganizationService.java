package erp.services;

import erp.domains.Animal;
import erp.domains.Enclosure;
import erp.factories.FeedingScheduleFactory;
import erp.interfaces.IAlive;
import erp.interfaces.IEnclosure;
import erp.storages.EnclosureStorage;
import erp.storages.ScheduleStorage;

public class FeedingOrganizationService {
    private final EnclosureStorage enclosureStorage;
    private final ScheduleStorage scheduleStorage;

    public FeedingOrganizationService(EnclosureStorage enclosureStorage, ScheduleStorage scheduleStorage) {
        this.enclosureStorage = enclosureStorage;
        this.scheduleStorage = scheduleStorage;
    }

    public void feedAnimal(IAlive animal) {
        animal.feed();
    }

    public void feedAnimalsInEnclosure(IEnclosure enclosure) {
        enclosure.getAnimals().stream().forEach(animal -> feedAnimal(animal));
    }

    public void feedAllANimals(EnclosureStorage zoo) {
        enclosureStorage.getEnclosures().stream().forEach(enclosure -> feedAnimalsInEnclosure(enclosure));
    }
}

package erp.application.services;

import erp.Infrastructure.interfaces.IEnclosureStorage;
import erp.Infrastructure.interfaces.IFeedingScheduleStorage;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IFeedingSchedule;

import static erp.shared.utils.Date.getLocalDateTimeStr;

public class FeedingOrganizationService {
    private final IEnclosureStorage enclosureStorage;
    private final IFeedingScheduleStorage feedingScheduleStorage;

    public FeedingOrganizationService(IEnclosureStorage enclosureStorage, IFeedingScheduleStorage feedingScheduleStorage) {
        this.enclosureStorage = enclosureStorage;
        this.feedingScheduleStorage = feedingScheduleStorage;
    }

    public void feedAnimal(IAnimal animal) {
        animal.feed();
    }

    public void feedAnimalsInEnclosure(IEnclosure enclosure) {
        enclosure.getAnimals().stream().forEach(animal -> feedAnimal(animal));
    }

    public void feedAllAnimals() {
        enclosureStorage.getEnclosures().stream()
                .forEach(enclosure -> feedAnimalsInEnclosure(enclosure));
    }

    public void feedAnimalBySchedule(IFeedingSchedule feedingSchedule) {
        feedAnimal(feedingSchedule.getAnimal());
        feedingSchedule.markCompleted(getLocalDateTimeStr());
    }

    public void feedAllANimalsBySchedule() {
        feedingScheduleStorage.getFeedingSchedules().stream()
                .forEach(schedule -> feedAnimalBySchedule(schedule));
    }
}

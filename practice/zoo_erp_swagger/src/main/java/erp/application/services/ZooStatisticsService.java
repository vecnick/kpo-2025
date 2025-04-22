package erp.application.services;

import erp.Infrastructure.interfaces.IEnclosureStorage;
import erp.Infrastructure.interfaces.IFeedingScheduleStorage;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IFeedingSchedule;

import java.util.List;
import java.util.stream.Collectors;

import static erp.shared.utils.CompareUtil.tryCompareRecursively;

public class ZooStatisticsService {
    private final IEnclosureStorage enclosureStorage;
    private final IFeedingScheduleStorage feedingScheduleStorage;

    public ZooStatisticsService(IEnclosureStorage enclosureStorage, IFeedingScheduleStorage feedingScheduleStorage) {
        this.enclosureStorage = enclosureStorage;
        this.feedingScheduleStorage = feedingScheduleStorage;
    }

    public List<IEnclosure> getEnclosures() {
        return enclosureStorage.getEnclosures();
    }

    public List<IFeedingSchedule> getFeedingSchedules() {
        return feedingScheduleStorage.getFeedingSchedules();
    }

    public List<IAnimal> getAnimals() {
        return getEnclosures().stream()
                .flatMap(enclosure -> enclosure.getAnimals().stream())
                .collect(Collectors.toList());
    }

    public List<IAnimal> getNonFeedingSchedules() {
        List<IAnimal> animals = getAnimals();
        List<IAnimal> scheduledAnimals = getFeedingSchedules().stream()
                .map(IFeedingSchedule::getAnimal).collect(Collectors.toList());

        return animals.stream()
                .filter(animal ->
                        scheduledAnimals.stream().noneMatch(scheduled -> tryCompareRecursively(animal, scheduled)))
                .collect(Collectors.toList());
    }

    public List<IAnimal> getHungryAnimals() {
        return getAnimals().stream().filter(IAnimal::isHungry).collect(Collectors.toList());
    }

    public List<IEnclosure> getDirtyEnclosures() {
        return getEnclosures().stream().filter(IEnclosure::isDirty).collect(Collectors.toList());
    }



}

package erp.application.services;

import erp.infrastructure.interfaces.IAnimalStorage;
import erp.infrastructure.interfaces.IEnclosureStorage;
import erp.infrastructure.interfaces.IFeedingScheduleStorage;
import erp.application.interfaces.IZooStatisticsService;
import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IFeedingSchedule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static erp.shared.utils.CompareUtil.tryCompareRecursively;

@Service
public class ZooStatisticsService implements IZooStatisticsService {
    private final IEnclosureStorage enclosureStorage;
    private final IFeedingScheduleStorage feedingScheduleStorage;
    private final IAnimalStorage animalStorage;

    public ZooStatisticsService(IEnclosureStorage enclosureStorage, IFeedingScheduleStorage feedingScheduleStorage, IAnimalStorage animalStorage) {
        this.enclosureStorage = enclosureStorage;
        this.feedingScheduleStorage = feedingScheduleStorage;
        this.animalStorage = animalStorage;
    }

    @Override
    public List<IEnclosure> getEnclosures() {
        return enclosureStorage.getEnclosures();
    }

    @Override
    public List<IFeedingSchedule> getFeedingSchedules() {
        return feedingScheduleStorage.getFeedingSchedules();
    }

    @Override
    public List<IAnimal> getAnimals() {
        return animalStorage.getAnimals();
    }

    @Override
    public List<IAnimal> getEnclosuresAnimals() {
        return getEnclosures().stream()
                .flatMap(enclosure -> enclosure.getAnimals().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<IAnimal> getNonEnclosuresAnimals() {
        List<IAnimal> animals = getAnimals();
        List<IAnimal> enclosuresAnimals = getEnclosuresAnimals();

        return animals.stream()
                .filter(animal ->
                        enclosuresAnimals.stream().noneMatch(enclosureAnimal -> tryCompareRecursively(animal, enclosureAnimal)))
                .collect(Collectors.toList());
    }

    @Override
    public List<IAnimal> getSchedulesAnimals() {
        return getFeedingSchedules().stream()
                .map(schedule -> schedule.getAnimal()).collect(Collectors.toList());
    }

    @Override
    public List<IAnimal> getNonSchedulesAnimals() {
        List<IAnimal> animals = getAnimals();
        List<IAnimal> schedulesAnimals = getSchedulesAnimals();

        return animals.stream()
                .filter(animal ->
                        schedulesAnimals.stream().noneMatch(scheduleAnimal -> tryCompareRecursively(animal, scheduleAnimal)))
                .collect(Collectors.toList());
    }

    @Override
    public List<IAnimal> getHungryAnimals() {
        return getAnimals().stream().filter(IAnimal::isHungry).collect(Collectors.toList());
    }

    @Override
    public List<IAnimal> getSickAnimals() {
        return getAnimals().stream().filter(IAnimal::isSick).collect(Collectors.toList());
    }

    @Override
    public List<IEnclosure> getDirtyEnclosures() {
        return getEnclosures().stream().filter(IEnclosure::isDirty).collect(Collectors.toList());
    }
}

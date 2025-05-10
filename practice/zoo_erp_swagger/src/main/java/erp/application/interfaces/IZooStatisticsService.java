package erp.application.interfaces;

import erp.domain.interfaces.IAnimal;
import erp.domain.interfaces.IEnclosure;
import erp.domain.interfaces.IFeedingSchedule;

import java.util.List;
import java.util.stream.Collectors;

import static erp.shared.utils.CompareUtil.tryCompareRecursively;

public interface IZooStatisticsService {

    List<IEnclosure> getEnclosures();
    List<IFeedingSchedule> getFeedingSchedules();
    List<IAnimal> getAnimals();
    List<IAnimal> getEnclosuresAnimals();
    List<IAnimal> getNonEnclosuresAnimals();
    List<IAnimal> getSchedulesAnimals();
    List<IAnimal> getNonSchedulesAnimals();
    List<IAnimal> getHungryAnimals();
    List<IAnimal> getSickAnimals();
    List<IEnclosure> getDirtyEnclosures();
}

package erp.application.services;

import erp.infrastructure.interfaces.IFeedingScheduleStorage;
import erp.application.interfaces.IFeedingOrganizationService;
import erp.domain.interfaces.IFeedingSchedule;
import org.springframework.stereotype.Service;

import static erp.shared.utils.Date.getLocalDateTimeStr;

@Service
public class FeedingOrganizationService implements IFeedingOrganizationService {
    private final IFeedingScheduleStorage feedingScheduleStorage;

    public FeedingOrganizationService(IFeedingScheduleStorage feedingScheduleStorage) {
        this.feedingScheduleStorage = feedingScheduleStorage;
    }

    @Override
    public void feedAnimalBySchedule(IFeedingSchedule feedingSchedule) {
        feedingSchedule.getAnimal().feed();
        feedingSchedule.markCompleted(getLocalDateTimeStr());
    }

    @Override
    public void feedAllANimalsBySchedule() {
        feedingScheduleStorage.getFeedingSchedules().stream()
                .forEach(schedule -> feedAnimalBySchedule(schedule));
    }
}

package erp.application.interfaces;

import erp.domain.interfaces.IFeedingSchedule;

import static erp.shared.utils.Date.getLocalDateTimeStr;

public interface IFeedingOrganizationService {
    void feedAnimalBySchedule(IFeedingSchedule feedingSchedule);
    void feedAllANimalsBySchedule();
}
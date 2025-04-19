package zooapi.zooerp2.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zooapi.zooerp2.Domain.Entities.CommonStats;
import zooapi.zooerp2.Domain.Interfaces.Application.StatServiceI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.AnimalRepositoryI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.EnclosureRepositoryI;
import zooapi.zooerp2.Domain.Interfaces.Infrastructure.FeedingRepositoryI;

@Component
@RequiredArgsConstructor
public class StatService implements StatServiceI {
    private final AnimalRepositoryI animalRepository;
    private final EnclosureRepositoryI enclosureRepository;
    private final FeedingRepositoryI feedingRepository;

    @Override
    public CommonStats getCommonStats() {
        var ret = new CommonStats();

        ret.setTotalAnimals(animalRepository.getAnimalCount());
        ret.setMaxAnimals(enclosureRepository.getEnclosureCapacity());
        ret.setTotalEnclosures(enclosureRepository.getEnclosureCount());
        ret.setFeedingsCount(feedingRepository.getFeedingScheduleCount());

        return ret;
    }
}

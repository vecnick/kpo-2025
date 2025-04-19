package zooapi.zooerp2.Domain.Events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class AnimalMovedEvent {
    int animalId;
    int fromEnclosure;
    int toEnclosure;
    LocalDateTime localTime;

    public AnimalMovedEvent(int animalId, int fromEnclosure, int toEnclosure, LocalDateTime localTime) {
        this.animalId = animalId;
        this.fromEnclosure = fromEnclosure;
        this.toEnclosure = toEnclosure;
        this.localTime = localTime;
    }
}

package zooapi.zooerp2.Domain.Events;

import java.time.LocalDateTime;


public class FeedingTimeEvent {
    int feedingId;
    LocalDateTime localTime;

    public FeedingTimeEvent(int feedingId, LocalDateTime localTime) {
        this.feedingId = feedingId;
        this.localTime = localTime;
    }
}

package erp.services;

import erp.domains.Thing;
import erp.interfaces.IThingStorage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ZooThingService {
    IThingStorage thingStorage;

    public  ZooThingService(IThingStorage thingStorage) {
        this.thingStorage = thingStorage;
    }

    public List<Integer> getThingsNumber() {
        return thingStorage.getThings().stream()
                .map(Thing::getNumber)
                .collect(Collectors.toList());
    }
}

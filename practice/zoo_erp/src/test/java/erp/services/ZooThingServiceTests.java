package erp.services;

import erp.domains.Thing;
import erp.domains.things.Computer;
import erp.domains.things.Table;
import erp.services.ZooThingService;
import erp.services.ZooThingStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ZooThingServiceTests {

    @Autowired
    ZooThingService zooThingService;
    @Mock
    ZooThingStorage mockZooThingStorage;

    @Test
    @DisplayName("ZooThingService - тест получения коллекции номеров предметов зоопарка (getThingsNumber)")
    void getThingsNumberTest() {
        Computer computer = new Computer(36);
        Table table = new Table(28);
        List<Thing> things = List.of(computer, table);

        List<Integer> thingsNumber = List.of(36, 28);

        // Перехватываем вызов getThings и подставляем своё значение для return
        when(mockZooThingStorage.getThings()).thenReturn(things);

        zooThingService = new ZooThingService(mockZooThingStorage);

        List<Integer> recievedThingsNumber = zooThingService.getThingsNumber();

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedThingsNumber).usingRecursiveComparison().isEqualTo(thingsNumber);
    }
}

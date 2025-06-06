package erp.services;

import erp.domains.Animal;
import erp.domains.Predator;
import erp.domains.Thing;
import erp.domains.animals.Wolf;
import erp.domains.things.Computer;
import erp.services.VetClinic;
import erp.services.ZooAnimalStorage;
import erp.services.ZooThingStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ZooThingStorageTests {

    @Autowired
    ZooThingStorage zooThingStorage;

    @Test
    @DisplayName("ZooThingStorage - тест добавления предметов (addThing)")
    void addThingTest() {
        Computer computer = new Computer(36);
        boolean wasAdded = zooThingStorage.addThing(computer);

        List<Thing> things = new ArrayList<>();
        things.add(computer);

        assertTrue(wasAdded);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooThingStorage).extracting("things")
                .usingRecursiveComparison().isEqualTo(things);
    }

    @Test
    @DisplayName("ZooAnimalStorage - тест получения и удаления предмета из коллекции по номеру (takeThing)")
    void takeThingByNumberTest() throws IllegalAccessException, NoSuchFieldException {
        Computer computer = new Computer(36);
        List<Thing> things = new ArrayList<>();
        things.add(computer);

        List<Thing> emptyThings = new ArrayList<>();

        // Подмена значения приватного поля
        Field vinField1 = ZooThingStorage.class.getDeclaredField("things");
        vinField1.setAccessible(true);
        vinField1.set(zooThingStorage, things);

        Thing recievedThing = zooThingStorage.takeThing(36);

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedThing).usingRecursiveComparison().isEqualTo(computer);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooThingStorage).extracting("things")
                .usingRecursiveComparison().isEqualTo(emptyThings);

    }

    @Test
    @DisplayName("ZooAnimalStorage - тест получения и удаления предметов из коллекции по их полному соответствию (takeThing)")
    void takeThingByThingTest() throws IllegalAccessException, NoSuchFieldException {
        Computer computer = new Computer(36);
        List<Thing> things = new ArrayList<>();
        things.add(computer);

        List<Thing> emptyThings = new ArrayList<>();

        // Подмена значения приватного поля
        Field vinField1 = ZooThingStorage.class.getDeclaredField("things");
        vinField1.setAccessible(true);
        vinField1.set(zooThingStorage, things);

        Thing recievedThing = zooThingStorage.takeThing(computer);

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedThing).usingRecursiveComparison().isEqualTo(computer);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooThingStorage).extracting("things")
                .usingRecursiveComparison().isEqualTo(emptyThings);
    }
}

package erp.services;

import erp.domains.Animal;
import erp.domains.Herbo;
import erp.domains.Predator;
import erp.domains.animals.Monkey;
import erp.domains.animals.Rabbit;
import erp.domains.animals.Tiger;
import erp.domains.animals.Wolf;
import erp.services.ZooAnimalService;
import erp.services.ZooAnimalStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ZooAnimalServiceTests {

    @Autowired
    ZooAnimalService zooAnimalService;
    @Mock
    ZooAnimalStorage mockZooAnimalStorage;

    @Test
    @DisplayName("ZooAnimalService - тест получения количества еды для животных в зоопарке (getTotalFood)")
    void getTotalFoodTest() {
        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        Rabbit rabbit = new Rabbit(new Herbo(10), 30, "rabbitName");
        Tiger tiger = new Tiger(new Predator(), 575, "tigerName");
        Monkey monkey = new Monkey(new Predator(), 100, "monkeyName");
        List<Animal> animals = List.of(wolf, rabbit, tiger, monkey);

        // Перехватываем вызов getAnimals и подставляем своё значение для return
        when(mockZooAnimalStorage.getAnimals()).thenReturn(animals);

        zooAnimalService = new ZooAnimalService(mockZooAnimalStorage);

        int recievedTotalFood = zooAnimalService.getTotalFood();

        assertEquals(recievedTotalFood, 904);
    }

    @Test
    @DisplayName("ZooAnimalService - тест получения животных для контактного зоопарка (getInteractiveAnimals)")
    void getInteractiveAnimalsTest() {
        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        Rabbit rabbit = new Rabbit(new Herbo(10), 30, "rabbitName");
        Tiger tiger = new Tiger(new Predator(), 575, "tigerName");
        Monkey monkey = new Monkey(new Predator(), 100, "monkeyName");
        List<Animal> animals = List.of(wolf, rabbit, tiger, monkey);

        List<Animal> interactiveAnimals = new ArrayList<>();
        interactiveAnimals.add(rabbit);

        // Перехватываем вызов getAnimals и подставляем своё значение для return
        when(mockZooAnimalStorage.getAnimals()).thenReturn(animals);

        zooAnimalService = new ZooAnimalService(mockZooAnimalStorage);

        List<Animal> recievedInteractiveAnimals = zooAnimalService.getInteractiveAnimals();

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedInteractiveAnimals).usingRecursiveComparison().isEqualTo(interactiveAnimals);
    }

    @Test
    @DisplayName("ZooAnimalService - тест получения количества животных в зоопарке (getAnimalsNumber)")
    void getAnimalsNumberTest() {
        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        Rabbit rabbit = new Rabbit(new Herbo(10), 30, "rabbitName");
        Tiger tiger = new Tiger(new Predator(), 575, "tigerName");
        Monkey monkey = new Monkey(new Predator(), 100, "monkeyName");
        List<Animal> animals = List.of(wolf, rabbit, tiger, monkey);

        // Перехватываем вызов getAnimals и подставляем своё значение для return
        when(mockZooAnimalStorage.getAnimals()).thenReturn(animals);

        zooAnimalService = new ZooAnimalService(mockZooAnimalStorage);

        int recievedAnimalsNumber = zooAnimalService.getAnimalsNumber();

        assertEquals(recievedAnimalsNumber, 4);
    }

    @Test
    @DisplayName("ZooAnimalService - тест получения коллекции имён животных зоопарка (getAnimalsIdentificationName)")
    void getAnimalsIdentificationNameTest() {
        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        Rabbit rabbit = new Rabbit(new Herbo(10), 30, "rabbitName");
        Tiger tiger = new Tiger(new Predator(), 575, "tigerName");
        Monkey monkey = new Monkey(new Predator(), 100, "monkeyName");
        List<Animal> animals = List.of(wolf, rabbit, tiger, monkey);

        List<String> animalsIdentificationName = List.of("wolfName", "rabbitName", "tigerName", "monkeyName");

        // Перехватываем вызов getAnimals и подставляем своё значение для return
        when(mockZooAnimalStorage.getAnimals()).thenReturn(animals);

        zooAnimalService = new ZooAnimalService(mockZooAnimalStorage);

        List<String> recievedAnimalsIdentificationName = zooAnimalService.getAnimalsIdentificationName();

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedAnimalsIdentificationName).usingRecursiveComparison().isEqualTo(animalsIdentificationName);
    }
}

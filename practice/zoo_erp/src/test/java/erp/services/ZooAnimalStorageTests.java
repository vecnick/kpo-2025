package erp.services;

import erp.domains.Animal;
import erp.domains.Predator;
import erp.domains.animals.Rabbit;
import erp.domains.animals.Wolf;
import erp.services.VetClinic;
import erp.services.ZooAnimalStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ZooAnimalStorageTests {

    @Autowired
    ZooAnimalStorage zooAnimalStorage;
    @Mock
    VetClinic mockVetClinic;

    @Test
    @DisplayName("ZooAnimalStorage - тест добавление животных в зоопарк без клиники и без параметров (addAnimal)")
    void addAnimalNoParamTest() {
        zooAnimalStorage = new ZooAnimalStorage(null);

        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        boolean wasAdded = zooAnimalStorage.addAnimal(wolf);

        List<Animal> animals = new ArrayList<>(); 
        animals.add(wolf);

        assertTrue(wasAdded);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooAnimalStorage).extracting("animals")
                .usingRecursiveComparison().isEqualTo(animals);
    }

    @Test
    @DisplayName("ZooAnimalStorage - тест добавление животных в зоопарк без клиники c параметрами (addAnimal)")
    void addAnimalAnyParamTest() {
        zooAnimalStorage = new ZooAnimalStorage(null);

        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        boolean wasAdded = zooAnimalStorage.addAnimal(wolf, 10);

        List<Animal> animals = new ArrayList<>(); 
        animals.add(wolf);

        assertTrue(wasAdded);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooAnimalStorage).extracting("animals")
                .usingRecursiveComparison().isEqualTo(animals);
    }

    @Test
    @DisplayName("ZooAnimalStorage - тест добавление животных в зоопарк с клиникой c параметрами, возвращающими false (addAnimal)")
    void addAnimalApproverFalseParamTest() {
        // Перехватываем вызов isApproved и подставляем своё значение для return
        when(mockVetClinic.isApproved(anyInt())).thenReturn(false);
        zooAnimalStorage = new ZooAnimalStorage(mockVetClinic);

        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        boolean wasAdded = zooAnimalStorage.addAnimal(wolf, 1);

        List<Animal> animals = new ArrayList<>();

        assertFalse(wasAdded);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooAnimalStorage).extracting("animals")
                .usingRecursiveComparison().isEqualTo(animals);
    }

    @Test
    @DisplayName("ZooAnimalStorage - тест добавление животных в зоопарк с клиникой c параметрами, возвращающими true (addAnimal)")
    void addAnimalApproverTrueParamTest() {
        // Перехватываем вызов isApproved и подставляем своё значение для return
        when(mockVetClinic.isApproved(anyInt())).thenReturn(true);
        zooAnimalStorage = new ZooAnimalStorage(mockVetClinic);

        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        boolean wasAdded = zooAnimalStorage.addAnimal(wolf, 1);

        List<Animal> animals = new ArrayList<>();
        animals.add(wolf);

        assertTrue(wasAdded);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooAnimalStorage).extracting("animals")
                .usingRecursiveComparison().isEqualTo(animals);
    }

    @Test
    @DisplayName("ZooAnimalStorage - тест получения и удаления животного из коллекции по имени (takeAnimal)")
    void takeAnimalByNameTest() throws IllegalAccessException, NoSuchFieldException {
        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        List<Animal> animals = new ArrayList<>();
        animals.add(wolf);

        List<Animal> emptyAnimals = new ArrayList<>();

        // Подмена значения приватного поля
        Field vinField1 = ZooAnimalStorage.class.getDeclaredField("animals");
        vinField1.setAccessible(true);
        vinField1.set(zooAnimalStorage, animals);

        Animal recievedAnimal = zooAnimalStorage.takeAnimal("wolfName");

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedAnimal).usingRecursiveComparison().isEqualTo(wolf);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooAnimalStorage).extracting("animals")
                .usingRecursiveComparison().isEqualTo(emptyAnimals);

    }

    @Test
    @DisplayName("ZooAnimalStorage - тест получения и удаления животного из коллекции по их полному соответствию (takeAnimal)")
    void takeAnimalByAnimalTest() throws IllegalAccessException, NoSuchFieldException {
        Wolf wolf = new Wolf(new Predator(), 199, "wolfName");
        List<Animal> animals = new ArrayList<>();
        animals.add(wolf);

        List<Animal> emptyAnimals = new ArrayList<>();

        // Подмена значения приватного поля
        Field vinField1 = ZooAnimalStorage.class.getDeclaredField("animals");
        vinField1.setAccessible(true);
        vinField1.set(zooAnimalStorage, animals);

        Animal recievedAnimal = zooAnimalStorage.takeAnimal(wolf);

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(recievedAnimal).usingRecursiveComparison().isEqualTo(wolf);
        // Рекурсивная проверка соотвествия значений приватного поля класса и эталонного
        assertThat(zooAnimalStorage).extracting("animals")
                .usingRecursiveComparison().isEqualTo(emptyAnimals);
    }
}

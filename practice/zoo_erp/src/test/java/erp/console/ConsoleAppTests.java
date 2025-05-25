package erp.console;

import erp.domains.Animal;
import erp.domains.Predator;
import erp.domains.Thing;
import erp.domains.animals.Tiger;
import erp.domains.things.Computer;
import erp.services.ZooAnimalService;
import erp.services.ZooAnimalStorage;
import erp.services.ZooThingService;
import erp.services.ZooThingStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ConsoleAppTests {
    @Mock
    private ZooAnimalService mockAnimalService;
    @Mock
    private ZooAnimalStorage mockAnimalStorage;
    @Mock
    private ZooThingService mockThingService;
    @Mock
    private ZooThingStorage mockThingStorage;
    @Mock
    private ConsoleApp mockConsoleApp;
    @Autowired
    private ConsoleApp consoleApp;

    private Scanner scanner = new Scanner(System.in);

    @Test
    @DisplayName("ConsoleApp - тест получения количества еды для животных через консоль (consoleGetTotalFood)")
    void consoleGetTotalFoodTest() {
        // Перехватываем вызов getTotalFood и подставляем своё значение для return
        when(mockAnimalService.getTotalFood()).thenReturn(10);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetTotalFood(mockAnimalService);

        assertEquals(out.toString(), "10\n");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения контактных животных через консоль (consoleGetInteractiveAnimals)")
    void consoleGetInteractiveAnimalsTest() {
        List<Animal> animals = new ArrayList<>();

        // Перехватываем вызов getInteractiveAnimals и подставляем своё значение для return
        when(mockAnimalService.getInteractiveAnimals()).thenReturn(animals);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetInteractiveAnimals(mockAnimalService);

        assertEquals(out.toString(), "");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения количества животных через консоль (consoleGetAnimalsNumber)")
    void consoleGetAnimalsNumberTest() {
        // Перехватываем вызов getAnimalsNumber и подставляем своё значение для return
        when(mockAnimalService.getAnimalsNumber()).thenReturn(10);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetAnimalsNumber(mockAnimalService);

        assertEquals(out.toString(), "10\n");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения имён контактных животных через консоль (consoleGetAnimalsIdentificationName)")
    void consoleGetAnimalsIdentificationNameTest() {
        List<String> animals = List.of("name1", "name2");

        // Перехватываем вызов getAnimalsIdentificationName и подставляем своё значение для return
        when(mockAnimalService.getAnimalsIdentificationName()).thenReturn(animals);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetAnimalsIdentificationName(mockAnimalService);

        assertEquals(out.toString   (), "name1\nname2\n");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест добавления животного в зоопарк через консоль (consoleAddAnimal)")
    void consoleAddAnimalTest() {
        // Перехватываем вызов consoleGenerateAnimal и подставляем своё значение для return
        when(mockConsoleApp.consoleGenerateAnimal(any())).thenReturn(null);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        mockConsoleApp.consoleAddAnimal(scanner, mockAnimalStorage);

        assertEquals(out.toString(), "");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения и удаления животного по его имени через консоль (consoleTakeAnimal)")
    void consoleTakeAnimalTest() {
        // Перехватываем вызов takeAnimal и подставляем своё значение для return
        when(mockAnimalStorage.takeAnimal(anyString())).thenReturn(null);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Подмена ввода
        ByteArrayInputStream in = new ByteArrayInputStream("10\n".getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);

        consoleApp.consoleTakeAnimal(scanner, mockAnimalStorage);

        assertEquals(out.toString(), "(name): Животное с таким именем не найдено!!!\n");

        // Восстанавливаем потоки
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения животных через консоль (consoleGetAnimals)")
    void consoleGetAnimalsTest() {
        List<Animal> animals = new ArrayList<>();

        // Перехватываем вызов getAnimals и подставляем своё значение для return
        when(mockAnimalStorage.getAnimals()).thenReturn(animals);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetAnimals(mockAnimalStorage);

        assertEquals(out.toString(), "");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения номеров предметов через консоль (consoleGetThingsNumber)")
    void consoleGetThingsNumberTest() {
        List<Integer> things = List.of(212, 5);

        // Перехватываем вызов getThingsNumber и подставляем своё значение для return
        when(mockThingService.getThingsNumber()).thenReturn(things);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetThingsNumber(mockThingService);

        assertEquals(out.toString(), "212\n5\n");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест добавления предмета через консоль (consoleAddThing)")
    void consoleAddThingTest() {
        // Перехватываем вызов consoleGenerateThing и подставляем своё значение для return
        when(mockConsoleApp.consoleGenerateThing(any())).thenReturn(null);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        mockConsoleApp.consoleAddThing(scanner, mockThingStorage);

        assertEquals(out.toString(), "");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения и удаления предмета по его номеру через консоль (consoleTakeThing)")
    void consoleTakeThingTest() {
        // Перехватываем вызов takeThing и подставляем своё значение для return
        when(mockThingStorage.takeThing(anyInt())).thenReturn(null);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Подмена ввода
        ByteArrayInputStream in = new ByteArrayInputStream("10\n".getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);

        consoleApp.consoleTakeThing(scanner, mockThingStorage);

        assertEquals(out.toString(), "(number): Вещь с таким номером не найдена!!!\n");

        // Восстанавливаем потоки
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест получения предметов через консоль (consoleGetThings)")
    void consoleGetThingsTest() {
        List<Thing> things = new ArrayList<>();

        // Перехватываем вызов getThings и подставляем своё значение для return
        when(mockThingStorage.getThings()).thenReturn(things);

        // Подмена вывода
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        consoleApp.consoleGetThings(mockThingStorage);

        assertEquals(out.toString(), "");

        // Восстанавливаем вывод
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ConsoleApp - тест создания животного через консоль (consoleGenerateAnimal)")
    void consoleGenerateAnimalTest() {
        Tiger animal = new Tiger(new Predator(), 10, "tigerName");

        // Подмена ввода
        ByteArrayInputStream in = new ByteArrayInputStream("predator\n10\ntigerName\ntiger\n".getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);

        Animal generatedAnimal = consoleApp.consoleGenerateAnimal(scanner);

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(generatedAnimal).usingRecursiveComparison().isEqualTo(animal);

        // Восстанавливаем ввод
        System.setIn(System.in);
    }

    @Test
    @DisplayName("ConsoleApp - тест создания предмета через консоль (consoleGenerateThing)")
    void consoleGenerateThingTest() {
        Thing thing = new Computer(10);

        // Подмена ввода
        ByteArrayInputStream in = new ByteArrayInputStream("10\ncomputer\n".getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);

        Thing generatedThing = consoleApp.consoleGenerateThing(scanner);

        // Проверяет на соответствие всех полей 1-ого и 2-ого объекта
        assertThat(generatedThing).usingRecursiveComparison().isEqualTo(thing);

        // Восстанавливаем ввод
        System.setIn(System.in);
    }
}

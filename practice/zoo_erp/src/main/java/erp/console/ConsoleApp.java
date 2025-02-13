package erp.console;

import erp.domains.Animal;
import erp.domains.Herbo;
import erp.domains.Predator;
import erp.domains.Thing;
import erp.domains.animals.Monkey;
import erp.domains.animals.Rabbit;
import erp.domains.animals.Tiger;
import erp.domains.animals.Wolf;
import erp.domains.things.Computer;
import erp.domains.things.Table;
import erp.interfaces.IAnimalType;
import erp.services.*;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleApp {
     public void start() {
        VetClinic vetClinic = new VetClinic();

        ZooAnimalStorage animalStorage = new ZooAnimalStorage(vetClinic);
        ZooAnimalService animalService = new ZooAnimalService(animalStorage);

        ZooThingStorage thingStorage = new ZooThingStorage();
        ZooThingService thingService = new ZooThingService(thingStorage);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");
            String command = scanner.nextLine().trim();

            switch (command.toLowerCase()) {
                // ZooAnimalService
                case "gettotalfood" -> consoleGetTotalFood(animalService);
                case "getinteractiveanimals" -> consoleGetInteractiveAnimals(animalService);
                case "getanimalsnumber" -> consoleGetAnimalsNumber(animalService);
                case "getanimalsidentificationname" -> consoleGetAnimalsIdentificationName(animalService);
                // ZooAnimalStorage
                case "addanimal" -> consoleAddAnimal(scanner, animalStorage);
                case "takeanimal" -> consoleTakeAnimal(scanner, animalStorage);
                case "getanimals" -> consoleGetAnimals(animalStorage);
                // ZooThingService
                case "getthingsnumber" -> consoleGetThingsNumber(thingService);
                // ZooThingStorage
                case "addthing" -> consoleAddThing(scanner, thingStorage);
                case "takething" -> consoleTakeThing(scanner, thingStorage);
                case "getthings" -> consoleGetThings(thingStorage);
                default -> System.out.println("Ошибка: неизвестная команда");
            }
            System.out.println("-----------");
        }

    }

    // ZooAnimalService
    public void consoleGetTotalFood(ZooAnimalService animalService) {
        System.out.println(animalService.getTotalFood());
    }
    public void consoleGetInteractiveAnimals(ZooAnimalService animalService) {
        animalService.getInteractiveAnimals().stream().map(Animal::toString).forEach(System.out::println);
    }
    public void consoleGetAnimalsNumber(ZooAnimalService animalService) {
        System.out.println(animalService.getAnimalsNumber());
    }
    public void consoleGetAnimalsIdentificationName(ZooAnimalService animalService) {
        animalService.getAnimalsIdentificationName().stream().forEach(System.out::println);
    }

    // ZooAnimalStorage
    public void consoleAddAnimal(Scanner scanner, ZooAnimalStorage animalStorage) {
        Animal animal = consoleGenerateAnimal(scanner);
        if (animal == null) { return; }

        System.out.print("(health): ");
        int health = scanner.nextInt();
        scanner.nextLine();

        boolean wasAdded = animalStorage.addAnimal(animal, health);
        if (wasAdded) {
            System.out.println("Животное успешно добавлено в зоопарк");
            return;
        }
        System.out.println("Животное не было добавлено из-за низкого здоровья!!!");
    }
        public void consoleTakeAnimal(Scanner scanner, ZooAnimalStorage animalStorage) {
            System.out.print("(name): ");
            String name = scanner.nextLine().trim();
            Animal animal = animalStorage.takeAnimal(name);

            if (animal == null) {
                System.out.println("Животное с таким именем не найдено!!!");
                return;
            }
            System.out.println(animal.toString());
        }
    public void consoleGetAnimals(ZooAnimalStorage animalStorage) {
        animalStorage.getAnimals().stream().map(Animal::toString).forEach(System.out::println);
    }

    // ZooThingService
    public void consoleGetThingsNumber(ZooThingService thingService) {
        thingService.getThingsNumber().stream().forEach(System.out::println);
    }

    // ZooThingStorage
    public void consoleAddThing(Scanner scanner, ZooThingStorage thingStorage) {
        Thing thing = consoleGenerateThing(scanner);
        if (thing == null) { return; }

        boolean wasAdded = thingStorage.addThing(thing);
        if (wasAdded) {
            System.out.println("Вещь успешна добавлена в зоопарк");
            return;
        }
        System.out.println("Вещь не была добавлена!!!");
    }
    public void consoleTakeThing(Scanner scanner, ZooThingStorage thingStorage) {
        System.out.print("(number): ");
        int number = scanner.nextInt();
        scanner.nextLine();

        Thing thing = thingStorage.takeThing(number);

        if (thing == null) {
            System.out.println("Вещь с таким номером не найдена!!!");
            return;
        }
        System.out.println(thing.toString());
    }
    public void consoleGetThings(ZooThingStorage animalStorage) {
        animalStorage.getThings().stream().map(Thing::toString).forEach(System.out::println);
    }

    // генераторы объектов по параметрам, вводимых в консоль
    public Animal consoleGenerateAnimal(Scanner scanner) {
        System.out.print("(type): ");
        String inputType = scanner.nextLine().trim();
        IAnimalType type = null;

        switch (inputType.toLowerCase()) {
            case "predator" -> type = new Predator();
            case "herbo" -> {
                System.out.print("(kindness): ");
                int inputKindness = scanner.nextInt();
                scanner.nextLine();
                type = new Herbo(inputKindness);
            }
            default -> {
                System.out.println("Ошибка: неизвестный тип животного");
                return null;
            }
        }

        System.out.print("(food): ");
        int food = scanner.nextInt();
        scanner.nextLine();

        System.out.print("(name): ");
        String name = scanner.nextLine().trim();

        System.out.print("(animal): ");
        String inputAnimal = scanner.nextLine().trim();
        Animal animal = null;

        switch (inputAnimal.toLowerCase()) {
            case "monkey" -> animal = new Monkey(type, food, name);
            case "rabbit" -> animal = new Rabbit(type, food, name);
            case "tiger" -> animal = new Tiger(type, food, name);
            case "wolf" -> animal = new Wolf(type, food, name);
            default -> {
                System.out.println("Ошибка: неизвестное животное");
                return null;
            }
        }
        return animal;
    }

    public Thing consoleGenerateThing(Scanner scanner) {
        System.out.print("(number): ");
        int number = scanner.nextInt();
        scanner.nextLine();

        System.out.print("(thing): ");
        String inputThing = scanner.nextLine().trim();
        Thing thing = null;

        switch (inputThing.toLowerCase()) {
            case "computer" -> thing = new Computer(number);
            case "table" -> thing = new Table(number);
            default -> {
                System.out.println("Ошибка: неизвестная вещь");
                return null;
            }
        }

        return thing;
    }
}
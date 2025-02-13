package hse.studying.zoo.services;

import hse.studying.zoo.domains.Animal;
import hse.studying.zoo.domains.Herbo;
import hse.studying.zoo.domains.Thing;
import hse.studying.zoo.domains.Tiger;
import hse.studying.zoo.domains.Monkey;
import hse.studying.zoo.domains.Wolf;
import hse.studying.zoo.domains.Rabbit;
import hse.studying.zoo.domains.Computer;
import hse.studying.zoo.domains.Table;
import hse.studying.zoo.factories.HerbivoreFactory;
import hse.studying.zoo.factories.PredatorFactory;
import hse.studying.zoo.factories.ThingFactory;
import hse.studying.zoo.params.HerbivoreParams;
import hse.studying.zoo.params.PredatorParams;
import hse.studying.zoo.params.ThingParams;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleMenu {
    private final Zoo zoo;
    private final PredatorFactory predatorFactory;
    private final HerbivoreFactory herbivoreFactory;
    private final ThingFactory thingFactory;
    private final Scanner scanner = new Scanner(System.in);
    private int inventoryItems = 0;

    public ConsoleMenu(Zoo zoo, PredatorFactory predatorFactory, HerbivoreFactory herbivoreFactory, ThingFactory thingFactory) {
        this.zoo = zoo;
        this.predatorFactory = predatorFactory;
        this.herbivoreFactory = herbivoreFactory;
        this.thingFactory = thingFactory;

        predatorFactory.register("tiger", Tiger::new);
        predatorFactory.register("wolf", Wolf::new);

        herbivoreFactory.register("monkey", Monkey::new);
        herbivoreFactory.register("rabbit", Rabbit::new);

        thingFactory.register("computer", Computer::new);
        thingFactory.register("table", Table::new);
    }

    public void start() {
        while (true) {
            System.out.println("1. Add an animal");
            System.out.println("2. Show all animals");
            System.out.println("3. Show total food consumption");
            System.out.println("4. Show animals for petting zoo");
            System.out.println("5. Add inventory");
            System.out.println("6. Show inventory");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addAnimal();
                case 2 -> showAnimals();
                case 3 -> System.out.println("Total food consumption: " + getTotalFoodConsumption() + " kg per day");
                case 4 -> showContactZooAnimals();
                case 5 -> addThing();
                case 6 -> showInventory();
                case 7 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid input!");
            }
        }
    }

    private void addAnimal() {
        System.out.print("Enter animal type: ");
        String type = scanner.next();
        System.out.print("Is the animal a herbivore or a predator? (h/p): ");
        String category = scanner.next().toLowerCase();
        System.out.print("Enter food consumption (kg): ");
        int food = scanner.nextInt();
        System.out.print("Enter weight (kg): ");
        int weight = scanner.nextInt();

        Animal animal;
        try {
            if (category.equals("h")) {
                System.out.print("Enter kindness level (1-10): ");
                int kindness = scanner.nextInt();
                animal = herbivoreFactory.create(type, new HerbivoreParams(food, weight, kindness, inventoryItems++));
            } else {
                animal = predatorFactory.create(type, new PredatorParams(food, weight, inventoryItems++));
            }
            if (zoo.addAnimal(animal)) {
                System.out.println(type + " added to the zoo.");
            } else {
                System.out.println(type + "  was not added. Did not pass the health test at the clinic.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(type + " was not added. " + e.getMessage());
        }
    }

    private void showAnimals() {
        if (zoo.getAnimals().isEmpty()) {
            System.out.println("No animals in the zoo.");
        } else {
            zoo.getAnimals().forEach(System.out::println);
        }
    }

    private int getTotalFoodConsumption() {
        return zoo.getAnimals().stream().mapToInt(Animal::getFoodConsumption).sum();
    }

    private void showContactZooAnimals() {
        System.out.println("Animals for petting zoo:");
        zoo.getAnimals().stream().filter(animal -> animal instanceof Herbo && ((Herbo) animal).getKindness() > 5).forEach(System.out::println);
    }

    private void addThing() {
        System.out.print("Enter thing type: ");
        String type = scanner.next();

        Thing thing = thingFactory.create(type, new ThingParams(inventoryItems++));
        zoo.addThing(thing);
        System.out.println(type + " added to inventory.");
    }

    private void showInventory() {
        System.out.println("Inventory:");
        zoo.getAnimals().forEach(animal -> System.out.println(animal.getClass().getSimpleName() + " #" + animal.getInventoryNumber()));
        zoo.getThings().forEach(thing -> System.out.println(thing.getClass().getSimpleName() + " #" + thing.getInventoryNumber()));
    }


}

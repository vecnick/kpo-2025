package hse.studying.zoo.services;

import hse.studying.zoo.domains.Animal;
import hse.studying.zoo.domains.Computer;
import hse.studying.zoo.domains.Herbo;
import hse.studying.zoo.domains.Monkey;
import hse.studying.zoo.domains.Rabbit;
import hse.studying.zoo.domains.Table;
import hse.studying.zoo.domains.Thing;
import hse.studying.zoo.domains.Tiger;
import hse.studying.zoo.domains.Wolf;
import hse.studying.zoo.factories.HerbivoreFactory;
import hse.studying.zoo.factories.PredatorFactory;
import hse.studying.zoo.factories.ThingFactory;
import hse.studying.zoo.params.HerbivoreParams;
import hse.studying.zoo.params.PredatorParams;
import hse.studying.zoo.params.ThingParams;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Provides a command-line interface for interacting with the zoo.
 * Allows users to add animals and things to the zoo, view the list of animals,
 * display the total food consumption, and show animals available for the petting zoo.
 */
@Component
public class ConsoleMenu {
    private final Zoo zoo;
    private final PredatorFactory predatorFactory;
    private final HerbivoreFactory herbivoreFactory;
    private final ThingFactory thingFactory;
    private final Scanner scanner = new Scanner(System.in);
    private int inventoryItems = 0;

    /**
     * ConsoleMenu provides a command-line interface for interacting with the zoo.
     * It allows users to add animals and things to the zoo, view the list of animals,
     * display the total food consumption, and show animals available for the petting zoo.
     *
     * @param zoo              the zoo instance to interact with
     * @param predatorFactory  the factory for creating predator animals
     * @param herbivoreFactory the factory for creating herbivore animals
     * @param thingFactory     the factory for creating things
     */
    public ConsoleMenu(Zoo zoo, PredatorFactory predatorFactory, HerbivoreFactory herbivoreFactory,
                       ThingFactory thingFactory) {
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

    /**
     * Starts the console menu service.
     */
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

    /**
     * Asks user to enter animal type, category, food consumption and weight,
     * creates an animal of the entered type and adds it to the zoo.
     */
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

    /**
     * Shows all animals in the zoo.
     */
    private void showAnimals() {
        if (zoo.getAnimals().isEmpty()) {
            System.out.println("No animals in the zoo.");
        } else {
            zoo.getAnimals().forEach(System.out::println);
        }
    }

    /**
     * Returns the total food consumption of all animals in the zoo.
     *
     * @return total food consumption in kg per day
     */
    private int getTotalFoodConsumption() {
        return zoo.getAnimals().stream().mapToInt(Animal::getFoodConsumption).sum();
    }

    /**
     * Displays animals that are suitable for the petting zoo.
     * Only herbivores with a kindness level greater than 5 are shown.
     */
    private void showContactZooAnimals() {
        System.out.println("Animals for petting zoo:");
        zoo.getAnimals().stream().filter(animal -> animal instanceof Herbo
                && ((Herbo) animal).getKindness() > 5).forEach(System.out::println);
    }

    /**
     * Asks user to enter thing type and adds it to the inventory.
     */
    private void addThing() {
        System.out.print("Enter thing type: ");
        String type = scanner.next();

        Thing thing = thingFactory.create(type, new ThingParams(inventoryItems++));
        zoo.addThing(thing);
        System.out.println(type + " added to inventory.");
    }

    /**
     * Displays the inventory of animals and things in the zoo.
     * Each item is shown with its type and inventory number.
     */
    private void showInventory() {
        System.out.println("Inventory:");
        zoo.getAnimals().forEach(animal ->
                System.out.println(animal.getClass().getSimpleName() + " #" + animal.getInventoryNumber()));
        zoo.getThings().forEach(thing ->
                System.out.println(thing.getClass().getSimpleName() + " #" + thing.getInventoryNumber()));
    }


}

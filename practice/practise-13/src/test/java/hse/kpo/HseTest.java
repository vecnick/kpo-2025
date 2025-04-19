package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.domains.cars.Car;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.facade.Hse;
import hse.kpo.factories.cars.HandCarFactory;
import hse.kpo.factories.cars.PedalCarFactory;
import hse.kpo.observers.SalesObserver;
import hse.kpo.services.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class HseTest {

    @Autowired
    private Hse hse;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PedalCarFactory pedalCarFactory;

    @Autowired
    private HandCarFactory handCarFactory;

    @Mock
    private SalesObserver salesObserver;

    @Test
    @DisplayName("Клиент с handPower=6 должен получить автомобиль с ручным приводом")
    void customerWithEnoughHandPower_ShouldGetHandCar() {
        // Arrange
        hse.addCustomer("Test", 3, 6, 100);
        hse.addHandCar();

        // Act
        hse.sell();

        // Assert
        Customer customer = customerService.getCustomers().get(0);
        Car receivedCar = customer.getCars().getFirst();

        assertAll(
                () -> assertNotNull(receivedCar, "Автомобиль не был назначен")
        );
    }

    @Test
    @DisplayName("Клиент с handPower=4 не должен получить автомобиль с ручным приводом")
    void customerWithLowHandPower_ShouldNotGetHandCar() {
        // Arrange
        hse.addCustomer("Test", 3, 4, 100);
        hse.addHandCar();

        // Act
        hse.sell();

        // Assert
        Customer customer = customerService.getCustomers().get(0);

        assertAll(
                () -> assertNull(customer.getCars(),
                        "Клиент не должен был получить автомобиль. Проверьте совместимость двигателя")
        );
    }

    @Test
    @DisplayName("При продаже двух автомобилей разным клиентам оба должны получить машины")
    void multipleCustomers_ShouldGetDifferentCars() {
        // Arrange
        hse.addCustomer("First", 3, 6, 100);
        hse.addCustomer("Second", 5, 7, 110);
        hse.addHandCar();
        hse.addHandCar();

        // Act
        hse.sell();

        // Assert
        List<Customer> customers = customerService.getCustomers();
        assertAll(
                () -> assertNotNull(customers.get(0).getCars().getFirst(),
                        "Первый клиент должен получить автомобиль"),
                () -> assertNotNull(customers.get(1).getCars().getFirst(),
                        "Второй клиент должен получить автомобиль"),
                () -> assertNotEquals(customers.get(0).getCars().getFirst().getVin(),
                        customers.get(1).getCars().getFirst().getVin(),
                        "VIN автомобилей должны отличаться")
        );
    }

    @Test
    @DisplayName("Отчет должен содержать информацию о продажах")
    void report_ShouldContainSalesInformation() {
        // Arrange
        // Добавляем клиента с параметрами, подходящими для педального автомобиля
        hse.addCustomer("TestClient", 7, 5, 100); // legPower=7 > 5 (требование PedalEngine)

        // Добавляем автомобиль с педальным двигателем (размер педалей 6)
        hse.addPedalCar(6);

        // Act
        hse.sell(); // Выполняем продажу
        String report = hse.generateReport(); // Генерируем отчет

        // Assert
        assertAll(() -> assertTrue(report.contains("TestClient"),
                "В отчете должно быть имя клиента"),
                () -> assertTrue(report.contains(ProductionTypes.CAR.toString()),
                        "В отчете должен быть указан тип продукции 'CAR'"),
                () -> assertTrue(report.matches("(?s).*VIN-\\d+.*"),
                        "Отчет должен содержать VIN автомобиля в формате 'VIN-число'"));
    }
}
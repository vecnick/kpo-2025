package hse.kpo;

import hse.kpo.domains.*;
import hse.kpo.services.CustomerStorage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CustomerStorageTests {

	// Внедрение зависимостей в объекты классов
	@Autowired
	private CustomerStorage customerStorage;

	@Test
	@DisplayName("CustomerStorage - тест добавления покупателей (addCustomer)")
	void addCustomerTest() {
		Customer customer1 = new Customer("Ivan",6,6, 200);
		Customer customer2 = new Customer("Ivan",1,1, 50);

		List<Customer> customers =  List.of(customer1, customer2);

		customerStorage.addCustomer(customer1);
		customerStorage.addCustomer(customer2);

		// Рекурсивная проверка приватных полей класса и полей у полей класса...
		assertThat(customerStorage)
				.extracting("customers")
				.usingRecursiveComparison()
				.isEqualTo(customers);
	}

	@Test
	@DisplayName("CustomerStorage - тест получения получения непустых покупателей (getCustomer)")
	void getCustomerNotEmptyTest() throws NoSuchFieldException, IllegalAccessException {
		Customer customer1 = new Customer("Ivan",6,6, 200);
		Customer customer2 = new Customer("Ivan",1,1, 50);
		customer1.setCar(new Car(1, new HandEngine()));
		customer2.setCar(null);

		List<Customer> customers =  List.of(customer1, customer2);

		// Подмена значения приватного поля
		Field vinField = CustomerStorage.class.getDeclaredField("customers");
		vinField.setAccessible(true);
		vinField.set(customerStorage, customers);

		List<Customer> recievedCustomers = customerStorage.getCustomers();

		// Проверяет на соответствие всех полей 1-ого и 2-ого объекта
		assertThat(recievedCustomers).usingRecursiveComparison().isEqualTo(customers);
	}

	@Test
	@DisplayName("CustomerStorage - тест получения получения пустых покупателей (getCustomer)")
	void getCustomerEmptyTest() throws NoSuchFieldException, IllegalAccessException {
		List<Customer> customers =  new ArrayList<>();

		List<Customer> recievedCustomers = customerStorage.getCustomers();

		// Проверяет на соответствие всех полей 1-ого и 2-ого объекта
		assertThat(recievedCustomers).usingRecursiveComparison().isEqualTo(customers);
	}
}
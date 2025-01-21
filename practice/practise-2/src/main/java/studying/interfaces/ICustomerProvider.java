package studying.interfaces;

import studying.domains.Customer;

import java.util.List;

public interface ICustomerProvider {
    List<Customer> getCustomers(); // метод возвращает коллекцию только для чтения, так как мы не хотим давать вызывающему коду возможность изменять список
}

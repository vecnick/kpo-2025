package studying.interfaces;

import studying.domains.Customer;

import java.util.List;

public interface ICustomerProvider {

    /**
     * Метод для получения всех покупателей.
     * 
     * @return - список покупателей
     */
    List<Customer> getCustomers(); // метод возвращает коллекцию только для чтения, так как мы не хотим давать вызывающему коду возможность изменять список
}

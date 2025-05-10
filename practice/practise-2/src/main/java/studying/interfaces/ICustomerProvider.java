package studying.interfaces;

import studying.domains.Customer;

import java.util.List;

/**
 * Интерфейс сервиса управления покупателями
 */
public interface ICustomerProvider {
    /**
     * Возвращает пользователей
     *
     * @return возвращает коллекцию пользователей только для чтения
     */
    List<Customer> getCustomers(); // метод возвращает коллекцию только для чтения, так как мы не хотим давать вызывающему коду возможность изменять список
}

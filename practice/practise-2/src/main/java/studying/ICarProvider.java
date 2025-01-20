package studying;

import java.util.Optional;

public interface ICarProvider {

    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}

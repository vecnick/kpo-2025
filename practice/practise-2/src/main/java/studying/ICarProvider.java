/**
 * Interface for providers that provide cars to customers.
 * 
 */
package studying;

/**
 * Interface for providers that provide cars to customers.
 * 
 */
public interface ICarProvider {

    /**
     * Takes a car from the list that is compatible with the customer.
     * 
     * @param customer the customer to check
     * @return the car if it is compatible, null otherwise
     */
    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}
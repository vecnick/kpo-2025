/**
 * Interface for providers that provide customers to other classes.
 * 
 */
package studying;

import java.util.List;

/**
 * Interface for providers that provide customers to other classes.
 * 
 */
public interface ICustomerProvider {
    /**
     * Returns a read-only collection of customers.
     * 
     * @return a read-only collection of customers
     */
    List<Customer> getCustomers();
}


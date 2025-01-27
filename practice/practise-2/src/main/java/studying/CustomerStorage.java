/**
 * Class for storing customers.
 * Implements {@link ICustomerProvider} interface.
 *
 * 
 * 
 */
public class CustomerStorage implements ICustomerProvider{
    /**
     * List of customers.
     */
    @Getter
    private List<Customer> customers = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Adds a customer to the storage.
     *
     * @param customer customer to add
     */
    public void addCustomer(Customer customer)
    {
        customers.add(customer); // simply add customer to the list
    }
}


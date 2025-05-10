package hse.kpo.interfaces;

import hse.kpo.domains.Customer;
import hse.kpo.enums.ProductionTypes;

public interface SalesObserver {
    public void onSale(Customer customer, ProductionTypes productType, int vin);

    void checkCustomers();
}

package hse.kpo.interfaces;

import hse.kpo.Enums.Types;
import hse.kpo.Report.Report;
import hse.kpo.domains.Customer;

public interface SalesObserver {
    void onSale(Customer customer, Types productType, int vin);
    Report buildReport();

    void checkCustomers();
}

package hse.kpo.interfaces;

import hse.kpo.Enums.Types;
import hse.kpo.Report.Report;
import hse.kpo.domains.Customer;

public interface CreationObserver {
    void onCreation(Types productType, int vin);
    Report buildReport();
}
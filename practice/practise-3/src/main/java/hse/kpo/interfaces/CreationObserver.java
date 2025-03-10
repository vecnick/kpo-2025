package hse.kpo.interfaces;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Reports.Report;

public interface CreationObserver {
    void onCreation(Types productType, int vin);
    Report buildReport();
}
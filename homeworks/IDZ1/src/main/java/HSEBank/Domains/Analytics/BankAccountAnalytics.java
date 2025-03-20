package HSEBank.Domains.Analytics;

import HSEBank.Enums.OperationTypes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class BankAccountAnalytics {
    int bankAccountId;
    LocalDate startDate;
    LocalDate endDate;
    double expensesSum, incomeSum;
    public Map<OperationTypes, Map<String, List<Double>>> valuesByCategories;

    public BankAccountAnalytics(int bankAccountId, LocalDate startDate, LocalDate endDate) {
        this.bankAccountId = bankAccountId;
        this.startDate = startDate;
        this.endDate = endDate;
        expensesSum = 0;
        incomeSum = 0;
        valuesByCategories = new HashMap<>();
    }

    public void addValue(OperationTypes type, String category, double sum) {
        valuesByCategories.putIfAbsent(type, new HashMap<>());

        valuesByCategories.get(type).compute(category, (key, list) -> {
            if (list == null) {
                return new ArrayList<>(Arrays.asList(sum, 0.0, 0.0));
            } else {
                list.set(0, list.get(0) + sum);
                return list;
            }
        });
    }
}

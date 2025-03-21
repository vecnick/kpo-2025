package hse.kpo.analytics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import hse.kpo.entities.Category;
import hse.kpo.entities.Operation;
import hse.kpo.enums.OperationType;
import hse.kpo.facades.BankFacade;
import hse.kpo.facades.CategoryFacade;
import hse.kpo.facades.OperationFacade;
import hse.kpo.types.Id;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BankAnalytics {
    
    BankFacade facade;
    OperationFacade operationFacade;
    CategoryFacade categoryFacade;

    public int countProfitbyPeriod(Id id, Date begin, Date end)
    {
        int accum = 0;
        for (Operation elem : operationFacade.getOperationsByBankAccountId(id)) {
            if (elem.getDate().compareTo(begin) >= 0 && elem.getDate().compareTo(end) <= 0)
            {
                accum += elem.getType() == OperationType.INCOME ? elem.getAmount() : -1 * elem.getAmount();
            }
        }
        return accum;
    }

    public Map<Category, Integer> moneyByCategories()
    {
        Map<Category, Integer> res = new HashMap<Category, Integer>();
        for (Operation elem : operationFacade.getOperations()) {
            Category category = categoryFacade.getCategorybyId(elem.getCategory_id()).get();
            Integer curr = res.get(category);
            if (curr == null)
            {
                res.put(category, elem.getAmount());
            }
            else
            {
                res.put(category, curr + elem.getAmount());
            }
        }
        return res;
    }

}

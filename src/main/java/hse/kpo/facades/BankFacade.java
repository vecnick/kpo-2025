package hse.kpo.facades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Operation;
import hse.kpo.enums.OperationCreationStatus;
import hse.kpo.entities.Category;
import hse.kpo.exceptions.ValueException;
import hse.kpo.params.BankAccountParams;
import hse.kpo.params.CategoryParams;
import hse.kpo.params.OperationParams;
import hse.kpo.types.Id;
import lombok.AllArgsConstructor;



@Component
@AllArgsConstructor
public class BankFacade {
    BankAccountFacade accounts;
    CategoryFacade categories;
    OperationFacade operations;

    public List<BankAccount> getBankAccounts()
    {
        return accounts.getBankAccounts();
    }
    public void createBankAccount(BankAccountParams params) throws ValueException
    {
        accounts.createBankAccount(params);
    }
    public void removeBankAccount(Id id)
    {
        accounts.removeBankAccount(id);
        var operations_to_delete = operations.getOperationsByBankAccountId(id);
        for (Operation operation : operations_to_delete) {
            operations.removeOperation(operation.getId());
        }
    }
    public void addSumToAccount(Id id, int delta) {
        accounts.addSumToAccount(id, delta);
    }

    public List<Category> getCategories()
    {
        return categories.getCategories();
    }
    public void createCategory(CategoryParams params) throws ValueException
    {
        categories.createCategory(params);
    }
    public void removeCategory(Id id)
    {
        categories.removeCategory(id);
        var operations_to_delete = operations.getOperationsByCategoryId(id);
        for (Operation operation : operations_to_delete) {
            operations.removeOperation(operation.getId());
        }
    }

    public List<Operation> getOperations()
    {
        return operations.getOperations();
    }
    public OperationCreationStatus createOperation(OperationParams params) throws ValueException
    {
        System.out.println(params.category_id());
        if (categories.getCategorybyId(params.category_id()).isEmpty())
        {
            return OperationCreationStatus.WRONG_CATEGORY;
        }
        if (accounts.getBankAccountbyId(params.bank_account_id()).isEmpty())
        {
            return OperationCreationStatus.WRONG_ACCOUNT;
        }
        operations.createOperation(params);
        return OperationCreationStatus.SUCCESS;
    }
    public void removeOperation(Id id)
    {
        operations.removeOperation(id);
    } 

}

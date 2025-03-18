package bank.domains;

import bank.enums.OperationType;
import lombok.Getter;
import lombok.ToString;

/**
 * Операция
 */
@ToString
@Getter
public class Operation {
    private int id;
    private OperationType type;
    private BankAccount bank_account_id;
    private int amount;
    private String date;
    private String description;
    private Category category_id;

    /**
     * Создание операции
     *
     * @param id - id операции
     * @param type - тип операции (доход или расход)
     * @param bank_account_id - счёт, к которому относится операция
     * @param amount - сумма операции
     * @param date - дата операции
     * @param description - описание операции
     * @param category_id - категория, к которому относится операция
     */
    public Operation(int id, OperationType type, BankAccount bank_account_id, int amount, String date, String description, Category category_id) {
        this.id = id;
        this.type = type;
        this.bank_account_id = bank_account_id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category_id = category_id;
    }

}

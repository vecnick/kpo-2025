package hse.studying.zoo.domains;

import lombok.ToString;

@ToString(callSuper = true)
public class Table extends Thing {
    public Table(int inventoryNumber) {
        super(inventoryNumber);
    }
}

package HSEBank.Enums;

public enum OperationTypes {
    INCOME("INCOME"),
    EXPENSE("EXPENSE");

    private final String description;

    OperationTypes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

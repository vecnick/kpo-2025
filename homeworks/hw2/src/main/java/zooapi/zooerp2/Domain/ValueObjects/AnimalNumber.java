package zooapi.zooerp2.Domain.ValueObjects;

public record AnimalNumber(int number) {
    public AnimalNumber {
        if (number < 0) {
            throw new IllegalArgumentException("Animal number cannot be negative");
        }
    }

    public boolean lessThan(int other) {
        return number < other;
    }
}

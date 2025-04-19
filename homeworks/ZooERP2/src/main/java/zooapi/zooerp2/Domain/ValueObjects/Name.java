package zooapi.zooerp2.Domain.ValueObjects;

public record Name(String name) {
    public Name {
        if (name != null && name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (!name.matches("[A-Z][a-zA-Z]*")) {
            throw new IllegalArgumentException("Name must start with capital letter and must not contain contain" +
                    " anything but letters");
        }
    }
}

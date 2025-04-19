package zooapi.zooerp2.Domain.ValueObjects;

public record Size3D(int width, int depth, int height) {
    public Size3D {
        if (width <= 0 || depth <= 0 || height <= 0) {
            throw new IllegalArgumentException("Size can not be zero or negative");
        }
    }

    public int calcSquare() {
        return width * depth;
    }

    public int calcVolume() {
        return width * depth * height;
    }
}

package studying;

public class Engine {
    private int size;
    public static String TYPE = "ELECTRIC";

    public Engine(int size) {
        this.size = size;
    }

    public int getSize(){
        return size;
    }

    @Override
    public String toString() {

        return "Engine{" +
                "size=" + size +
                '}';
    }
}

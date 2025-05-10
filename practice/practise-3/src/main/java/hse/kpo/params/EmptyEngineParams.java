package hse.kpo.params;

/**
 * Пустая запись без параметров
 */
public record EmptyEngineParams() {
    public static final EmptyEngineParams DEFAULT = new EmptyEngineParams();
}

package hse.kpo.domains.params;

/**
 * Класс пустых параметров двигателя.
 */
public record EmptyEngineParams() {
    public static final EmptyEngineParams DEFAULT = new EmptyEngineParams();
}

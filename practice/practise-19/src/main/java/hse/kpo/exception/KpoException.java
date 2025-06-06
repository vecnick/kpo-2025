package hse.kpo.exception;

import lombok.Getter;

@Getter
public class KpoException extends RuntimeException {
    private final int code;

    public KpoException(int code, String message) {
        super(message);
        this.code = code;
    }
}
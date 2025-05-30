package hse.kpo.exception.handler;

import hse.kpo.exception.KpoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "hse.kpo")
public class KpoExceptionHandler {
    @ExceptionHandler(KpoException.class)
    public ResponseEntity<KpoException> handleKpoException(KpoException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getCode()))
                .body(ex);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<KpoException> handleError(Error error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new KpoException(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getMessage()));
    }
}

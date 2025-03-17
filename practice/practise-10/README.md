# Занятие 8. DDD

## Цель занятия
- Изучение возможности общения с внешними сервисами, с помощью контролеров.
## Требования к реализации
1. 
## Тестирование
1. 
## Задание на доработку
- 
## Пояснения к реализации
Добавьте зависимости в build.gradle

```
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
```

```
package hse.kpo.exception.handler;

import hse.kpo.exception.KpoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
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
```

Откройте Postman и выполните импорт спецификации:

1) Нажмите кнопку file->Import в верхнем левом углу.
2) Выберите файл swagger.yaml (или openapi.yaml) через вкладку File.
3) Нажмите Import.

Создайте среду для тестирования:

1) Нажмите Environments в левом меню.
2) Нажмите Create Environment .
3) Укажите:
   - Name : Local 
   - Variables :
     * baseUrl: http://localhost:8080
4) Справа включите использование нужного Environment
5) Отправьте запросы к включенному сервису

```

```
<details> 
<summary>Ссылки</summary>
1. 
</details>
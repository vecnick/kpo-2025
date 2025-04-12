# Занятие 10. API testing

## Цель занятия
- Изучение возможности тестирования REST api с помощью тестов контроллеров и postman.
## Требования к реализации
1. 
## Тестирование
1. 
## Задание на доработку
- 
## Пояснения к реализации

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
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

   @Test
    @DisplayName("Создание педального автомобиля с валидными параметрами")
    void createPedalCar_ValidData_Returns2012() throws Exception {
        CarRequest request = new CarRequest("PEDAL", 10);

        String responseJson = mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CarResponse response = objectMapper.readValue(responseJson, CarResponse.class);
        assertAll(
                () -> assertNotNull(response.vin(), "VIN должен быть присвоен"),
                () -> assertEquals(EngineTypes.PEDAL.name(), response.engineType(),
                        "Тип двигателя должен быть PEDAL")
        );
    }
}
```
```
public record CarResponse(
Integer vin,
String engineType,
Integer pedalSize
) {}
```
<details> 
<summary>Ссылки</summary>
1. 
</details>
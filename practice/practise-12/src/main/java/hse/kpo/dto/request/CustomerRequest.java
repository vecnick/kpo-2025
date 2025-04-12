package hse.kpo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Min(value = 1, message = "Сила ног должна быть >= 1")
    private int legPower;

    @Min(value = 1, message = "Сила рук должна быть >= 1")
    private int handPower;

    @Min(value = 1, message = "IQ должен быть >= 1")
    private int iq;
}

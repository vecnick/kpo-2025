package zooapi.zooerp2.Domain.DTO.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class AnimalRequest {
    @Schema(description = "Тип животного (PREDATOR, HERBO, BIRD, FISH)", example = "HERBO")
    @Pattern(regexp = "PREDATOR|HERBO|BIRD|FISH", message = "Допустимые значения: PREDATOR, HERBO, BIRD, FISH")
    String animalType;

    @Schema(description = "Имя животного", example = "Rex")
    @Pattern(regexp = "[A-Z][a-zA-Z]*", message = "Имя должно начинаться с заглавной буквы, не должно содержать пробелов, состоять только из букв")
    String name;

    @Schema(description = "Пол животного (MALE, FEMALE)", example = "MALE")
    @Pattern(regexp = "MALE|FEMALE", message = "Допустимые значения: MALE, FEMALE")
    String sex;

    @Schema(description = "Тип еды (MEAT, HERB, FISHFOOD, INSECTS)", example = "HERB")
    @Pattern(regexp = "MEAT|HERB|FISHFOOD|INSECTS", message = "Допустимые значения: MEAT, HERB, FISHFOOD, INSECTS")
    String foodType;

    @Schema(description = "Дата рождения животного", example = "2020-04-15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date birthday;
}

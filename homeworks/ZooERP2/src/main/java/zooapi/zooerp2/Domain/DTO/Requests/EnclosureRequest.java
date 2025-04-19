package zooapi.zooerp2.Domain.DTO.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EnclosureRequest {
    @Schema(description = "Тип животного (PREDATOR, HERBO, BIRD, FISH)", example = "HERBO")
    @Pattern(regexp = "PREDATOR|HERBO|BIRD|FISH", message = "Допустимые значения: PREDATOR, HERBO, BIRD, FISH")
    String animalType;

    @Schema(description = "Высота вольера в метрах", example = "6")
    @Min(value = 1, message = "Минимальная высота вольера равна 1")
    int height;

    @Schema(description = "Ширина вольера в метрах", example = "6")
    @Min(value = 1, message = "Минимальная ширина вольера равна 1")
    int width;

    @Schema(description = "Глубина вольера в метрах", example = "6")
    @Min(value = 1, message = "Минимальная глубина вольера равна 1")
    int depth;

    @Schema(description = "Максимальное число животных в вольере", example = "6")
    @Min(value = 1, message = "Вольер должен вмещать хотя бы одно животное")
    int maxAnimalNumber;
}

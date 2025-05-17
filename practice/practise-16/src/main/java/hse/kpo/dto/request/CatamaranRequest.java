package hse.kpo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CatamaranRequest(
        @Schema(description = "Тип двигателя (PEDAL, HAND, LEVITATION)", example = "PEDAL")
        @Pattern(regexp = "PEDAL|HAND|LEVITATION", message = "Допустимые значения: PEDAL, HAND, LEVITATION")
        String engineType,

        @Schema(description = "Размер педалей (1-15)", example = "6")
        @Min(value = 1, message = "Минимальный размер педалей - 1")
        @Max(value = 15, message = "Максимальный размер педалей - 15")
        @Nullable
        Integer pedalSize
) {}

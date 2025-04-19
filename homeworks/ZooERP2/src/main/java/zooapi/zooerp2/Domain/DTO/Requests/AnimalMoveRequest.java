package zooapi.zooerp2.Domain.DTO.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AnimalMoveRequest {
    @Schema(description = "id животного", example = "6")
    @Min(value = 1, message = "Минимальный id равен 1")
    int animalId;

    @Schema(description = "id вольера", example = "6")
    @Min(value = 1, message = "Минимальный id равен 1")
    int enclosureId;
}

package zooapi.zooerp2.Domain.DTO.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import zooapi.zooerp2.Domain.Enums.FoodType;

import java.time.Duration;
import java.util.Date;

@Builder
@Getter
public class NewFeedingRequest {
    @Schema(description = "Id животного", example = "6")
    @Min(value = 1, message = "Минимальный id  животного раавен 1")
    int animalId;

    @Schema(description = "Начальное время для составления расписания", example = "2020-04-15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date startTime;

    @Schema(description = "Начальное время для составления расписания", example = "2020-04-15")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Duration delta;

    @Schema(description = "Тип еды (MEAT, HERB, FISHFOOD, INSECTS)", example = "HERB")
    @Pattern(regexp = "MEAT|HERB|FISHFOOD|INSECTS", message = "Допустимые значения: MEAT, HERB, FISHFOOD, INSECTS")
    String foodType;
}

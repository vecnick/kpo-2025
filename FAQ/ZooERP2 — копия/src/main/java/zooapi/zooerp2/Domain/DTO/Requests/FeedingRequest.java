package zooapi.zooerp2.Domain.DTO.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class FeedingRequest {
    @Schema(description = "Начальное время для составления расписания", example = "2020-04-15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date startDate;

    @Schema(description = "Конечная время для составления расписания", example = "2020-04-15")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date endDate;
}
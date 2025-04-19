package zooapi.zooerp2.Presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zooapi.zooerp2.Domain.Entities.CommonStats;
import zooapi.zooerp2.Domain.Interfaces.Application.StatServiceI;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "Статистика", description = "Получение статистики")
public class StatController {
    private final StatServiceI statService;

    @GetMapping("")
    @Operation(summary = "Вернуть базовую статистику зоопарка",
            description = "")
    public ResponseEntity<CommonStats> getGeneralStat() {
        var ret = statService.getCommonStats();
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }
}

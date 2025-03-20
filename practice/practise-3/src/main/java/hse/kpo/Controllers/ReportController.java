package hse.kpo.Controllers;

import hse.kpo.Enums.EngineTypes;
import hse.kpo.Facades.Hse;
import hse.kpo.Report.ReportBuilder;
import hse.kpo.Requests.CarRequest;
import hse.kpo.Requests.ShipRequest;
import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Engines.HandEngineI;
import hse.kpo.domains.Engines.LevitatingEngineI;
import hse.kpo.domains.Engines.PedalEngineI;
import hse.kpo.domains.Ships.Ship;
import hse.kpo.factories.Ships.ShipFactory;
import hse.kpo.interfaces.EngineI;
import hse.kpo.services.CarServiceI;
import hse.kpo.services.HseCarService;
import hse.kpo.services.HseShipService;
import hse.kpo.services.ShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Отчеты", description = "Управление отчетами")
public class ReportController {
    private final CarServiceI carStorage;
    private final HseCarService carService;
    private final Hse hseFacade;
    private final ShipService shipService;
    private final Hse hse;
    private final ShipFactory shipFactory;
    private final HseShipService hseShipService;
//    private final ReportBuilder reportBuilder;

    @PostMapping
    @Operation(summary = "Получить отчет")
    public ResponseEntity<String> getReport() {
        return ResponseEntity.ok(hse.generateReport());
    }
}

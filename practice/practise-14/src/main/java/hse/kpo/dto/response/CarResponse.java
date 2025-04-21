package hse.kpo.dto.response;

public record CarResponse(
        Integer vin,
        String engineType,
        Integer pedalSize
) {}

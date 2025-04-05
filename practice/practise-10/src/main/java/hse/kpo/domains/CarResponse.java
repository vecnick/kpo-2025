package hse.kpo.domains;

public record CarResponse(
        Integer vin,
        String engineType,
        Integer pedalSize
) {}
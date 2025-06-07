package hse.kpo.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record TrainingCompletedEvent(
    int customerId,
    String updatedParameter
) {}

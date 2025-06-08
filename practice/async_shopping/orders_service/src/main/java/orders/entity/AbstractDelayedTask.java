package orders.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import orders.enums.DelayedTaskStatus;
import orders.enums.DelayedTaskType;
import org.hibernate.annotations.ColumnTransformer;

@MappedSuperclass
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class AbstractDelayedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    String requestPayload;

    DelayedTaskStatus status;
    DelayedTaskType taskType;
    String createdDate;
}
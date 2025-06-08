package payments.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import payments.enums.DelayedTaskStatus;
import payments.enums.DelayedTaskType;

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

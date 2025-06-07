package orders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orders.enums.OutboxStatus;
import orders.enums.TaskType;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OutboxTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    String requestPayload;

    OutboxStatus status;
    TaskType taskType;
    String createdDate;
}
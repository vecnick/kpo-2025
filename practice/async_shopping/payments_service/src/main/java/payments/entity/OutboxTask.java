package payments.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "outbox_tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class OutboxTask extends AbstractDelayedTask {
}

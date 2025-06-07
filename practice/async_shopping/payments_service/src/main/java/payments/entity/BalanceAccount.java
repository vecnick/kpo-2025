package payments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceAccount {

    @Id
    public int userId; // у каждого пользователя ровно 1 счёт

    public int balance;
}

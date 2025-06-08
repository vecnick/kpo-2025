package payments.factory;

import org.springframework.stereotype.Component;
import payments.entity.BalanceAccount;
import payments.interfaces.IBalanceAccountFactory;

@Component
public class BalanceAccountFactory implements IBalanceAccountFactory {

    @Override
    public BalanceAccount create(int userId) {
        return new BalanceAccount(userId, 0);
    }

    @Override
    public BalanceAccount createEmpty() { return new BalanceAccount(0, 0); }
}

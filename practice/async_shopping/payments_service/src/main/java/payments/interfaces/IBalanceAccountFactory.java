package payments.interfaces;

import payments.entity.BalanceAccount;

public interface IBalanceAccountFactory {

    BalanceAccount create(int userId);
    BalanceAccount createEmpty();
}

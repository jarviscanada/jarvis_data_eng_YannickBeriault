package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import java.util.Map;

public class AccountUtil extends EntityUtil {

    private final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Unsupported operation.";

    private AccountDao accountDao;

    public AccountUtil(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Entity<?> createEntity(JsonObject jsonObject) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public Entity<?> createEntity(Map<String, Object> quoteMap) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    public Account createEmptyAccountWithTrader(Trader trader) {

        Account nuAccount = new Account();

        nuAccount.setTrader_id(trader.getId());
        nuAccount.setAmount(0d);

        return accountDao.save(nuAccount);
    }
}

package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraderAccountService {

    private static final String AMOUNT_ERROR_MESSAGE = "Funds have to be more " +
            "than zero.";
    private static final String CANNOT_DELETE_TRADER_ERROR_MESSAGE = "Cannot delete trader: ";

    private static final String NULL_TRADER_ID_ERROR_MESSAGE = "Trader id cannot be null.";
    private static final String OPEN_POSITIONS_ERROR_MESSAGE = "some positions remain open.";
    private static final String TRADER_ACCOUNT_NOT_FOUND_ERROR_MESSAGE = "Account was not found " +
            "for this trader.";
    private static final String TRADER_NOT_FOUND_ERROR_MESSAGE = "Trader was not found.";
    private static final String TRADER_NULL_FIELD_ERROR_MESSAGE = "Trader object cannot contain " +
            "null fields.";
    private static final String UNABLE_TO_UPDATE_ACCOUNT_ERROR_MESSAGE = "Unable to update " +
            "amount in account.";

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;
    private AccountUtil accountUtil;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
                                PositionDao positionDao, SecurityOrderDao securityOrderDao) {

        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
        this.accountUtil = new AccountUtil(accountDao);
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non-empty)
     * - create a trader
     * - create an account
     * - create, setup and return a new traderAccountView
     *
     * Assumption: to simplify the logic, each trader has only one account where
     * traderId == accountId
     *
     * @param trader cannot be null. No field can be null, except for id (auto-generated by db).
     * @return traderAccountView
     * @throws IllegalArgumentException if a trader has null fields of if id is null
     **/
    public TraderAccountView createTraderAndAccount(Trader trader) {

        Account traderAccount;

        validateTrader(trader);

        if (!traderDao.existsById(trader.getId()))
            trader = traderDao.save(trader);

        traderAccount = accountUtil.createEmptyAccountWithTrader(trader);

        return new TraderAccountView(trader, traderAccount);
    }

    private void validateTrader(Trader trader) {

        if (trader.getFirst_name() == null
                || trader.getLast_name() == null || trader.getDob() == null
                || trader.getCountry() == null || trader.getEmail() == null)
            throw new IllegalArgumentException(TRADER_NULL_FIELD_ERROR_MESSAGE);
    }

    /**
     * A trader can be deleted if it has no open position and 0 cash balance
     * - validate trader id
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId must not be null
     * @throws IllegalArgumentException if traderId is null or not found or unable to delete
     */
    public void deleteTraderById(Integer traderId) {

        Trader traderToDelete = validateTraderIdAndReturnTrader(traderId);;

        Account traderAccount = accountDao.findByTraderId(traderId)
                .orElseThrow(()
                        -> new IllegalArgumentException(TRADER_ACCOUNT_NOT_FOUND_ERROR_MESSAGE));

        List<SecurityOrder> securityOrdersOfAccount =
                securityOrderDao.getSecurityOrdersOfAccount(traderAccount.getId());
        if (positionDao.countPositionsOfAccount(traderAccount.getId())
                != securityOrdersOfAccount.size()) {

            throw new IllegalArgumentException(CANNOT_DELETE_TRADER_ERROR_MESSAGE
                    + OPEN_POSITIONS_ERROR_MESSAGE);
        }

        if (securityOrdersOfAccount.size() != 0)
            securityOrderDao.deleteAll(securityOrdersOfAccount);

        accountDao.deleteById(traderAccount.getId());
        traderDao.deleteById(traderToDelete.getId());
    }

    private Trader validateTraderIdAndReturnTrader(Integer traderId) {

        if (traderId == null)
            throw new IllegalArgumentException(NULL_TRADER_ID_ERROR_MESSAGE);

        return traderDao.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException(TRADER_NOT_FOUND_ERROR_MESSAGE));
    }

    /**
     * Deposit a fund to an account by traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId must not be null
     * @param funds must be greater than 0
     * @throws IllegalArgumentException if traderId is null, if trader is not found,
     *                                  or if fund is lesser than or equal to 0
     */
    public Account deposit(Integer traderId, Double funds) {

        validateTransactionFunds(funds);
        validateTraderIdAndLinkedAccount(traderId);

        return accountDao.addAmountById(traderId, funds).orElseThrow(()
               -> new IllegalArgumentException(UNABLE_TO_UPDATE_ACCOUNT_ERROR_MESSAGE)
        );
    }

    /**
     * Withdraw funds from an account by traderId
     *
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader ID
     * @param funds amount can't be 0
     * @return updated account
     * @throws IllegalArgumentException if traderId is null or not found,
     *                                  fund is less than or equal to 0,
     *                                  or account amount is insufficient
     */
    public Account withdraw(Integer traderId, Double funds) {

        validateTransactionFunds(funds);
        validateTraderIdAndLinkedAccount(traderId);

        return accountDao.addAmountById(traderId, funds * -1).orElseThrow(()
                -> new IllegalArgumentException(UNABLE_TO_UPDATE_ACCOUNT_ERROR_MESSAGE)
        );
    }

    private void validateTransactionFunds(Double funds) {

        if (funds <= 0)
            throw new IllegalArgumentException(AMOUNT_ERROR_MESSAGE);
    }

    private void validateTraderIdAndLinkedAccount(Integer traderId) {

        validateTraderIdAndReturnTrader(traderId);
        accountDao.findByTraderId(traderId).orElseThrow(()
                -> new IllegalArgumentException(TRADER_ACCOUNT_NOT_FOUND_ERROR_MESSAGE)
        );
    }
}
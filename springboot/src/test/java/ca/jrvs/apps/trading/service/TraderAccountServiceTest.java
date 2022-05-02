package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceTest {

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderAccountService traderAccountService;

    private Trader savedTrader;
    private Account savedAccount;
    private double startingAmount = 3000.00;

    @Before
    public void createAndSetTraderObject() {

        Trader nuTrader = new Trader();
        nuTrader.setFirst_name("Fulano");
        nuTrader.setLast_name("de Tal");
        nuTrader.setDob(new Date(78997689));
        nuTrader.setCountry("LaNada");
        nuTrader.setEmail("fulano@gmail.com");

        savedTrader = traderDao.save(nuTrader);
    }

    @After
    public void deleteEverything() {

        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void testCreateTraderAndAccount() {

        TraderAccountView traderAccountView
                = traderAccountService.createTraderAndAccount(savedTrader);

        assertTrue(savedTrader.getFirst_name()
                .equalsIgnoreCase(traderAccountView.getTrader().getFirst_name()));
        assertEquals(savedTrader.getId(),
                traderAccountView.getAccount().getTrader_id());
        assertEquals(0, traderAccountView.getAccount().getAmount(), 0.001);
    }

    @Test
    public void testDeposit() {

        double amountToDeposit = 500.38;

        traderAccountService.createTraderAndAccount(savedTrader);
        Account traderAccount = accountDao.findByTraderId(savedTrader.getId())
                .orElseThrow(IllegalArgumentException::new);

        accountDao.addAmountById(traderAccount.getId(), startingAmount);
        traderAccount = traderAccountService.deposit(savedTrader.getId(), amountToDeposit);

        assertEquals(startingAmount + amountToDeposit, traderAccount.getAmount()
                , 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositInvalidAmount() {

        double amountToDeposit = 0.0;

        traderAccountService.createTraderAndAccount(savedTrader);
        traderAccountService.deposit(savedTrader.getId(), amountToDeposit);
    }

    @Test
    public void testWithdraw() {

        double amountToDeposit = 777.28;

        traderAccountService.createTraderAndAccount(savedTrader);
        Account traderAccount = accountDao.findByTraderId(savedTrader.getId())
                .orElseThrow(IllegalArgumentException::new);

        accountDao.addAmountById(traderAccount.getId(), startingAmount);
        traderAccount = traderAccountService.withdraw(savedTrader.getId(), amountToDeposit);

        assertEquals(startingAmount - amountToDeposit, traderAccount.getAmount()
                , 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawInsufficientFunds() {

        double amountToDeposit = 5000.00;

        traderAccountService.createTraderAndAccount(savedTrader);
        Account traderAccount = accountDao.findByTraderId(savedTrader.getId())
                .orElseThrow(IllegalArgumentException::new);

        accountDao.addAmountById(traderAccount.getId(), startingAmount);
        traderAccountService.withdraw(savedTrader.getId(), amountToDeposit);
    }
}

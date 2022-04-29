package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    private Account savedAccount;
    private Trader savedTrader;

    @Before
    public void insertOne() {

        Trader nuTrader = new Trader();
        Account nuAccount = new Account();

        nuTrader.setFirst_name("Fulano");
        nuTrader.setLast_name("de Tal");
        nuTrader.setDob(new Date(78997689));
        nuTrader.setCountry("LaNada");
        nuTrader.setEmail("fulano@gmail.com");

        savedTrader = traderDao.save(nuTrader);

        nuAccount.setTrader_id(savedTrader.getId());
        nuAccount.setAmount(5000d);

        savedAccount = accountDao.save(nuAccount);
    }

    @After
    public void deleteAll() {

        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void testFindAllById() {

        List<Account> accounts = Lists.newArrayList(
                accountDao.findAllById(
                        List.of(savedAccount.getId())
                )
        );

        assertEquals(1, accounts.size());
        assertEquals(savedAccount.getTrader_id(), accounts.get(0).getTrader_id());
    }
}

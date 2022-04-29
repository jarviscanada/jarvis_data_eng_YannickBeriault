package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
}

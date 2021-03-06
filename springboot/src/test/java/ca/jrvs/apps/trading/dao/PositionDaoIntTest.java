package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.*;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoIntTest {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private QuoteDao quoteDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    private SecurityOrder savedSecurityOrder;
    private Quote savedQuote;
    private Account savedAccount;
    private Trader savedTrader;

    @Before
    public void insertNeededData() {

        Trader nuTrader = new Trader();
        Account nuAccount = new Account();
        SecurityOrder nuSecurityOrder = new SecurityOrder();
        Quote nuQuote = new Quote();

        nuTrader.setFirst_name("Fulano");
        nuTrader.setLast_name("de Tal");
        nuTrader.setDob(new Date(78997689));
        nuTrader.setCountry("LaNada");
        nuTrader.setEmail("fulano@gmail.com");

        savedTrader = traderDao.save(nuTrader);

        nuAccount.setTrader_id(savedTrader.getId());
        nuAccount.setAmount(5000d);

        savedAccount = accountDao.save(nuAccount);

        nuQuote.setAskPrice(10d);
        nuQuote.setAskSize(10);
        nuQuote.setBidPrice(10.2d);
        nuQuote.setBidSize(10);
        nuQuote.setId("aapl");
        nuQuote.setLatestPrice(10.1d);

        savedQuote = quoteDao.save(nuQuote);

        nuSecurityOrder.setAccount_id(savedAccount.getId());
        nuSecurityOrder.setStatus("FILLED");
        nuSecurityOrder.setTicker(savedQuote.getTicker());
        nuSecurityOrder.setSize(80);
        nuSecurityOrder.setPrice(905d);
        nuSecurityOrder.setNotes("This is a filled order.");

        savedSecurityOrder = securityOrderDao.save(nuSecurityOrder);
    }

    @After
    public void deleteAll() {

        securityOrderDao.deleteAll();
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void testFindAllById() {

        List<Position> positions = Lists.newArrayList(
                positionDao.findAllById(
                        List.of(savedAccount.getId())
                )
        );

        assertEquals(1, positions.size());
        assertTrue(savedQuote.getTicker()
                .equalsIgnoreCase(positions.get(0).getTicker())
        );
    }
}
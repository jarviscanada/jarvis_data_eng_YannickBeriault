package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
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
public class TraderDaoIntTest {

    @Autowired
    private TraderDao traderDao;

    private Trader savedTrader;

    @Before
    public void insertOne() {

        Trader nuTrader = new Trader();

        nuTrader.setFirst_name("Fulano");
        nuTrader.setLast_name("de Tal");
        nuTrader.setDob(new Date(78997689));
        nuTrader.setCountry("LaNada");
        nuTrader.setEmail("fulano@gmail.com");

        savedTrader = traderDao.save(nuTrader);
    }

    @After
    public void deleteAll() {
        traderDao.deleteAll();
    }

    @Test
    public void testFindAllById() {

        List<Trader> traders = Lists.newArrayList(
                traderDao.findAllById(
                        List.of(savedTrader.getId())
                )
        );

        assertEquals(1, traders.size());
        assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
    }
}
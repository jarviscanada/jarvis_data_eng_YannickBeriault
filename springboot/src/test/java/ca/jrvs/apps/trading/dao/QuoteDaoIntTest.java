package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;

    @Before
    public void insertOne() {

        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("aapl");
        savedQuote.setLatestPrice(10.1d);

        quoteDao.save(savedQuote);
    }

    @Test
    public void testUpdateAndFindById() {

        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(20);
        savedQuote.setId("aapl");
        savedQuote.setLatestPrice(10.1d);

        quoteDao.save(savedQuote);

        Quote updatedQuote = quoteDao.findById("aapl")
                .orElseThrow(()
                        -> new DataRetrievalFailureException("Unable to retrieve quote from database."));
        assertEquals(20, updatedQuote.getBidSize());
    }

    @Test
    public void testSaveAllAndFindAll() {

        String[] fakeQuoteTickers = new String[] {"blip", "blap",
                "blop", "cash", "smut", "zap", "boom"};
        LinkedList<Quote> weirdQuotes = new LinkedList<>();

        for (String fakeTicker : fakeQuoteTickers) {

            Quote weirdQuote = new Quote();
            weirdQuote.setAskPrice(10d);
            weirdQuote.setAskSize(10);
            weirdQuote.setBidPrice(10.2d);
            weirdQuote.setBidSize(20);
            weirdQuote.setId(fakeTicker);
            weirdQuote.setLatestPrice(10.1d);

            weirdQuotes.add(weirdQuote);
        }

        quoteDao.saveAll(weirdQuotes);

        List<Quote> allQuotes = quoteDao.findAll();
        for (String ticker : fakeQuoteTickers) {
            assertEquals(1, allQuotes.stream()
                    .filter(q -> q.getTicker().equalsIgnoreCase(ticker))
                    .count());
        }
    }

    @Test
    public void testExistsByIdTrue() {
        assertTrue(quoteDao.existsById("aapl"));
    }

    @Test
    public void testExistsByIdFalse() {
        assertFalse(quoteDao.existsById("wizz"));
    }

    @Test
    public void testSaveAllDeleteByIdAndCount() {

        String[] fakeQuoteTickers = new String[] {"copy", "cut",
                "save", "send", "find", "end", "exit", "quit"};
        LinkedList<Quote> weirdQuotes = new LinkedList<>();

        for (String fakeTicker : fakeQuoteTickers) {

            Quote weirdQuote = new Quote();
            weirdQuote.setAskPrice(10d);
            weirdQuote.setAskSize(10);
            weirdQuote.setBidPrice(10.2d);
            weirdQuote.setBidSize(20);
            weirdQuote.setId(fakeTicker);
            weirdQuote.setLatestPrice(10.1d);

            weirdQuotes.add(weirdQuote);
        }

        quoteDao.saveAll(weirdQuotes);

        quoteDao.deleteById("aapl");
        assertEquals(fakeQuoteTickers.length, quoteDao.count());
    }

    @Test
    public void testFindByIdEmpty() {

        assertFalse(quoteDao.findById("flop").isPresent());
    }

    @Test
    public void testDeleteAll() {

        String[] fakeQuoteTickers = new String[]{"bam", "zap",
                "flip", "wizz", "blop", "crak"};
        LinkedList<Quote> weirdQuotes = new LinkedList<>();

        for (String fakeTicker : fakeQuoteTickers) {

            Quote weirdQuote = new Quote();
            weirdQuote.setAskPrice(10d);
            weirdQuote.setAskSize(10);
            weirdQuote.setBidPrice(10.2d);
            weirdQuote.setBidSize(20);
            weirdQuote.setId(fakeTicker);
            weirdQuote.setLatestPrice(10.1d);

            weirdQuotes.add(weirdQuote);
        }

        quoteDao.saveAll(weirdQuotes);

        assertEquals(fakeQuoteTickers.length + 1, quoteDao.count());

        quoteDao.deleteAll();
        assertEquals(0, quoteDao.count());
    }

    @After
    public void deleteAll() {
        quoteDao.deleteAll();
    }
}
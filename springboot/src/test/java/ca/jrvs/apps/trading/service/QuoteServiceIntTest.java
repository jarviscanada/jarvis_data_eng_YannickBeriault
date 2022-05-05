package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    private final String[] goodQuoteTickers = new String[] {"tsla", "ibm", "sony", "caj", "amzn"};
    private final String[] notAllGoodQuoteTickers = new String[] {"scum", "tsla", "ibm", "sony", "caj", "amzn"};

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteDao quoteDao;

    @Before
    public void setup() {
        quoteDao.deleteAll();
    }

    @Test
    public void testFindIexQuoteByTicker() {

        String teslaTicker = "tsla";

        Quote goodQuote = quoteService.findIexQuoteByTicker(teslaTicker);
        assertTrue(teslaTicker.equalsIgnoreCase(goodQuote.getTicker()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindIexQuoteByTickerFail() {
        quoteService.findIexQuoteByTicker("blop");
    }

    @Test
    public void testUpdateMarketData() {

        double fakeValue = -8d;

        LinkedList<Quote> legitQuotes = new LinkedList<>();

        for (String fakeTicker : goodQuoteTickers) {

            Quote weirdQuote = new Quote();
            weirdQuote.setAskPrice(fakeValue);
            weirdQuote.setAskSize(10);
            weirdQuote.setBidPrice(10.2d);
            weirdQuote.setBidSize(20);
            weirdQuote.setId(fakeTicker);
            weirdQuote.setLatestPrice(10.1d);

            legitQuotes.add(weirdQuote);
        }

        quoteDao.saveAll(legitQuotes);
        quoteService.updateMarketData();

        for (String ticker : goodQuoteTickers)
            assertNotEquals(fakeValue, quoteService.findIexQuoteByTicker(ticker).getAskPrice(), 0.0001);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateMarketDataFail() {

        LinkedList<Quote> notAllLegitQuotes = new LinkedList<>();

        for (String fakeTicker : notAllGoodQuoteTickers) {

            Quote weirdQuote = new Quote();
            weirdQuote.setAskPrice(10d);
            weirdQuote.setAskSize(10);
            weirdQuote.setBidPrice(10.2d);
            weirdQuote.setBidSize(20);
            weirdQuote.setId(fakeTicker);
            weirdQuote.setLatestPrice(10.1d);

            notAllLegitQuotes.add(weirdQuote);
        }

        quoteDao.saveAll(notAllLegitQuotes);
        quoteService.updateMarketData();
    }

    @Test
    public void testSaveQuotesAndFinalAllQuotes() {

        List<String> tickersList = Arrays.asList(goodQuoteTickers);
        quoteService.saveQuotes(tickersList);
        List<Quote> goodQuotes = quoteService.findAllQuotes();

        for (Quote quote : goodQuotes)
            assertTrue(tickersList.stream()
                    .filter(ticker -> ticker.equalsIgnoreCase(quote.getTicker()))
                    .count() == 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveQuotesFail() {
        quoteService.saveQuotes(Arrays.asList(notAllGoodQuoteTickers));
    }

    @Test
    public void testSaveQuoteWithQuote() {

        String weirdTicker = "flap";

        Quote weirdQuote = new Quote();
        weirdQuote.setAskPrice(10d);
        weirdQuote.setAskSize(10);
        weirdQuote.setBidPrice(10.2d);
        weirdQuote.setBidSize(20);
        weirdQuote.setId(weirdTicker);
        weirdQuote.setLatestPrice(10.1d);

        quoteService.saveQuote(weirdQuote);

        assertEquals(10, quoteDao.findById(weirdTicker).get().getAskSize());
    }

    @Test
    public void testSaveQuoteWithTicker() {

        String teslaTicker = "tsla";

        quoteService.saveQuote(teslaTicker);
        assertTrue(quoteService.findAllQuotes().get(0).getTicker().equalsIgnoreCase(teslaTicker));
    }
}
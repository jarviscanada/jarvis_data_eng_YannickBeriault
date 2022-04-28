package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoIntTest {

    private final String[] goodQuoteTickers = new String[] {"amzn", "caj", "ibm", "sony", "tsla"};

    private MarketDataDao dao;

    @Before
    public void init() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        if (System.getProperty("IEX_PUB_TOKEN") != null)
            marketDataConfig.setToken(System.getProperty("IEX_PUB_TOKEN"));
        else
            marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void testFindIexQuotesByTickers() {

        List<IexQuote> quoteList = (List<IexQuote>) dao.findAllById(Arrays.asList("AAPL", "FB"));
        assertEquals(2, quoteList.size());
        assertEquals("AAPL", quoteList.get(0).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindIexQuotesByTickersFail() {

        dao.findAllById(Arrays.asList("AAPL", "FB2"));
    }

    @Test
    public void testFindByTicker() {

        String ticker = "AAPL";
        IexQuote quote = dao.findById(ticker).get();
        assertEquals(ticker, quote.getId());
    }

    @Test
    public void testExceptionMessageForSingleWrongTicker() {

        String badTicker = "blop";

        try {
            dao.findById(badTicker);
        } catch (DataRetrievalFailureException e) {
            assertEquals(MarketDataDao.NOT_FOUND_ERROR_MESSAGE, e.getMessage());
        }
    }

    @Test
    public void testFindAllById() {

        int counter = 0;
        Iterable<IexQuote> foundQuotes = dao.findAllById(Arrays.asList(goodQuoteTickers));

        for (String ticker : goodQuoteTickers) {

            Iterator<IexQuote> iterator = foundQuotes.iterator();
            while (iterator.hasNext()) {

                if (ticker.equalsIgnoreCase(iterator.next().getTicker()))
                    counter++;
            }
        }

        assertEquals(goodQuoteTickers.length, counter);
    }
}
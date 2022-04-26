package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MarketDataDaoIntTest {

    private MarketDataDao dao;

    @Before
    public void init() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getProperty("IEX_PUB_TOKEN"));

        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void testFindIexQuotesByTickers() {

        List<Quote> quoteList = (List<Quote>) dao.findAllById(Arrays.asList("AAPL", "FB"));
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
        Quote quote = dao.findById(ticker).get();
        assertEquals(ticker, quote.getId());
    }
}
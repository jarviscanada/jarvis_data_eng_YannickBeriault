package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.controller.QuoteController;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.service.QuoteService;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableTransactionManagement
public class AppConfig {

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public MarketDataConfig marketDataConfig() {

        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        return marketDataConfig;
    }

    @Bean
    public MarketDataDao marketDataDao(HttpClientConnectionManager httpClientConnectionManager,
                         MarketDataConfig marketDataConfig) {
        return new MarketDataDao(httpClientConnectionManager, marketDataConfig);
    }

    @Bean
    public QuoteService quoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        return new QuoteService(quoteDao, marketDataDao);
    }

    @Bean
    public QuoteController quoteController(QuoteService quoteService) {
        return new QuoteController(quoteService);
    }

    @Bean
    public QuoteDao quoteDao() {
        return new QuoteDao();
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        return cm;
    }
}

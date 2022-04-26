package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao", "ca.jrvs.apps.trading.service"})
public class TestConfig {

    @Bean
    public MarketDataConfig marketDataConfig() {

        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getProperty("IEX_PUB_TOKEN"));

        return marketDataConfig;
    }

    @Bean
    public DataSource dataSource() {

        System.out.println("Creating apacheDataSource");
        String url = System.getProperty("PSQL_URL");
        String user = System.getProperty("PSQL_USER");
        String password = System.getProperty("PSQL_PASSWORD");

        //Never log your credentials/secrets. Use IDE debugger instead
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);

        return cm;
    }
}

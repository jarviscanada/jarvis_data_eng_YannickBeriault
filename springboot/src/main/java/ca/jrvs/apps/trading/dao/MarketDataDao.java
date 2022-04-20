package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATCH = "/stock/market/batch?symbols=%s&types=quotes&token=";
    private static String IEX_BATCH_URL;
    private static final int OK_STATUS_CODE = 200;
    private static final int NOT_FOUND_CODE = 404;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
                         MarketDataConfig marketDataConfig) {

        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATCH + marketDataConfig.getToken();
    }

    /**
     * Get an IexQuote (helper method to class findAllById)
     *
     * @param ticker
     * @throws IllegalArgumentException if a given ticker is invalid
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public Optional<IexQuote> findById(String ticker) {

        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = (List<IexQuote>) findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0)
            return Optional.empty();
        else if (quotes.size() == 1)
            iexQuote = Optional.of(quotes.get(0));
        else
            throw new DataRetrievalFailureException("Unexpected number of quotes.");

        return iexQuote;
    }

    /**
     * Get quotes from IEX
     *
     * @param tickers
     * @return a list of IexQuote objects
     * @throws IllegalArgumentException if a any one ticker is invalid or if tickers is empty
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public Iterable<IexQuote> findAllById(Iterable<String> tickers) {

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = tickers.iterator();

        if (iterator.hasNext())
            stringBuilder.append(iterator.next());
        while (iterator.hasNext()) {

            stringBuilder.append(',');
            stringBuilder.append(iterator.next());
        }

        String url = String.format(IEX_BATCH_URL, stringBuilder);
        Optional<String> responseString = executeHttpGet(url);
    }

    private Optional<String> executeHttpGet(String url) {

        HttpClient httpClient = getHttpClient();
        HttpPost request = new HttpPost(url);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new DataRetrievalFailureException("HTTP request could not be completed.");
        }

        Optional<String> optional;
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == OK_STATUS_CODE) {

            try {
                optional = Optional.of(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new DataRetrievalFailureException("Response entity could not be parsed to string.");
            }
        } else if (statusCode == NOT_FOUND_CODE)
            optional = Optional.empty();
        else
            throw new DataRetrievalFailureException("Unexpected status code was returned.");

        return optional;
    }

    private CloseableHttpClient getHttpClient() {

        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> S save(S entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }
}

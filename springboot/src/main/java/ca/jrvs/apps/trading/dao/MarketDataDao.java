package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.IexQuoteUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

//@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATCH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private static String IEX_BATCH_URL;
    private static final int OK_STATUS_CODE = 200;
    private static final int NOT_FOUND_CODE = 404;

    private static final String WRONG_NUMBER_OF_QUOTES_ERROR_MESSAGE = "Unexpected number of quotes.";
    private static final String INCOMPLETE_HTTP_REQUEST_ERROR_MESSAGE = "HTTP request could not be completed.";
    private static final String RESPONSE_NOT_PARSED_ERROR_MESSAGE =
            "Response entity could not be parsed to string.";
    private static final String UNEXPECTED_STATUS_CODE_ERROR_MESSAGE = "Unexpected status code was returned.";
    private static final String JSON_STRING_CONVERSION_ERROR_MESSAGE =
            "Failed to convert response string to JSONObject.";
    private static final String NOT_FOUND_ERROR_MESSAGE =
            "One or more tickers did not correspond to any valid stock.";

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
            throw new DataRetrievalFailureException(WRONG_NUMBER_OF_QUOTES_ERROR_MESSAGE);

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
        int tickersCounter = 0;

        if (iterator.hasNext()) {

            stringBuilder.append(iterator.next().toLowerCase());
            tickersCounter++;
        }
        while (iterator.hasNext()) {

            stringBuilder.append(',');
            stringBuilder.append(iterator.next().toLowerCase());
            tickersCounter++;
        }

        String url = String.format(IEX_BATCH_URL, stringBuilder);
        Optional<String> responseString = executeHttpGet(url);

        if (!responseString.isPresent())
            throw new DataRetrievalFailureException(NOT_FOUND_ERROR_MESSAGE);

        JsonObject responseJson = convertResponseStringToJsonObject(responseString.get());
        if (responseJson == null)
            throw new DataRetrievalFailureException(JSON_STRING_CONVERSION_ERROR_MESSAGE);

        LinkedList<IexQuote> quotesList = produceQuotesList(responseJson);
        if (quotesList.size() != tickersCounter)
            throw new IllegalArgumentException(NOT_FOUND_ERROR_MESSAGE);

        return quotesList;
    }

    private Optional<String> executeHttpGet(String url) {

        HttpClient httpClient = getHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new DataRetrievalFailureException(INCOMPLETE_HTTP_REQUEST_ERROR_MESSAGE);
        }

        Optional<String> optional;
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == OK_STATUS_CODE) {

            try {
                optional = Optional.of(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new DataRetrievalFailureException(RESPONSE_NOT_PARSED_ERROR_MESSAGE);
            }
        } else if (statusCode == NOT_FOUND_CODE)
            optional = Optional.empty();
        else
            throw new DataRetrievalFailureException(UNEXPECTED_STATUS_CODE_ERROR_MESSAGE);

        return optional;
    }

    private CloseableHttpClient getHttpClient() {

        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
    }

    private JsonObject convertResponseStringToJsonObject(String responseString) {

        char firstBracket = responseString.charAt(0);
        JsonReader jsonReader = Json.createReader(new StringReader(responseString));

        if (firstBracket == '[') {

            JsonArray jsonArray;

            try (jsonReader) {
                jsonArray = jsonReader.readArray();
            }

            return jsonArray.getJsonObject(0);
        } else if (firstBracket == '{') {

            try (jsonReader) {
                return jsonReader.readObject();
            }
        } else
            return null;
    }

    private LinkedList<IexQuote> produceQuotesList(JsonObject responseJson) {

        LinkedList<IexQuote> list = new LinkedList<>();

        for (Map.Entry<String, JsonValue> entry : responseJson.entrySet()) {
            list.add(IexQuoteUtil.createIexQuote(entry.getValue().asJsonObject()
                    .getJsonObject("quote")));
        }

        return list;
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

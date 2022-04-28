package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.IexQuoteUtil;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.QuoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {

        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Update quote table against IEX table
     * - get all quote from the db
     * - for each ticker get IEX quote
     * - convert IEX quote to Quote entity
     * - persist quote to db
     *
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {

        List<Quote> quotesInDb = quoteDao.findAll();
        LinkedList<String> tickersInDb = new LinkedList<>();

        for (Quote quote : quotesInDb)
            tickersInDb.add(quote.getTicker());

        try {
            saveQuotes(tickersInDb);
        } catch (IllegalArgumentException | DataRetrievalFailureException e) {

            if (e.getMessage().equals(MarketDataDao.NOT_FOUND_ERROR_MESSAGE))
                throw new ResourceNotFoundException(e);
            else
                throw e;
        }
    }

    /**
     * Helper method. Map a IexQuote to a Quote entity.
     * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
     * Make sure to set a default value for number field(s).
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        return QuoteUtil.createQuote(iexQuote);
    }

    /**
     * Validate (agains IEX) and save given tickers to quote table.
     *
     * - Get iexQuote(s)
     * - convert each iexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers a list of tickers
     * @throws IllegalArgumentException if ticker is not found in IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {

        Iterable<IexQuote> quotesFromIex = marketDataDao.findAllById(tickers);
        LinkedList<Quote> convertedQuotes = new LinkedList<>();

        for (IexQuote iexQuote : quotesFromIex)
            convertedQuotes.add(buildQuoteFromIexQuote(iexQuote));

        return quoteDao.saveAll(convertedQuotes);
    }

    /**
     * Helper method.
     */
    public Quote saveQuote(String ticker) {

        return saveQuote(findIexQuoteByTicker(ticker));
    }

    /**
     * Find an IexQuote
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public Quote findIexQuoteByTicker(String ticker) {

        try {
            return buildQuoteFromIexQuote(marketDataDao.findById(ticker)
                    .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid."))
            );
        } catch (DataRetrievalFailureException e) {
            throw new IllegalArgumentException(ticker + " is invalid (" + e.getMessage() + ").");
        }
    }

    /**
     * Update a given quote without validation
     * @param quote Quote entity
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quotes table
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}
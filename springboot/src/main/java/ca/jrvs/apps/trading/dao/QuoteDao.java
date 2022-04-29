package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.QuoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final String FAILED_COUNT_QUERY_ERROR_MESSAGE = "Unable to retrieve " +
            "count of quotes (no number was returned).";
    private static final String FAILED_INSERT_ERROR_MESSAGE = "Failed to insert";
    private static final String FAILED_UPDATE_ERROR_MESSAGE = "Unable to update quote.";
    private static final String NOT_IMPLEMENTED_ERROR_MESSAGE = "Not implemented";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

    private QuoteUtil quoteUtil = new QuoteUtil();

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    /**
     * hint: http://bit.ly/2sDz8hq DataAccessException family
     *
     * @throws DataAccessException for unexpected SQL result or SQL execution failure
     */
    @Override
    public Quote save(Quote quote) {

        if (existsById(quote.getId())) {

            int updatedRowNumber = updateOne(quote);
            if (updatedRowNumber != 1)
                throw new DataRetrievalFailureException(FAILED_UPDATE_ERROR_MESSAGE);
        } else
            addOne(quote);

        return quote;
    }

    /**
     * helper method that saves one quote
     */
    private void addOne(Quote quote) {

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);

        if (row != 1)
            throw new IncorrectResultSizeDataAccessException(FAILED_INSERT_ERROR_MESSAGE, 1, row);
    }

    /**
     * helper method that updates one quote
     */
    private int updateOne(Quote quote) {

        String updateSql = "UPDATE quote set latest_price=?, bid_price=?, bid_size=?," +
                "ask_price=?, ask_size=? WHERE " + ID_COLUMN_NAME + "=?";

        return jdbcTemplate.update(updateSql, makeUpdateValues(quote));
    }

    /**
     * helper method that makes Sql update value objects
     *
     * @param quote to be updated
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Quote quote) {

        return new Object[]{quote.getLatestPrice(), quote.getBidPrice(), quote.getBidSize(),
                quote.getAskPrice(), quote.getAskSize(), quote.getId()};
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {

        LinkedList<Quote> savedQuotes = new LinkedList<>();

        for (Quote quote : quotes) {

            save(quote);
            savedQuotes.add(quote);
        }

        return (List<S>) savedQuotes;
    }

    @Override
    public List<Quote> findAll() {

        String findAllQuery = "SELECT * FROM " + TABLE_NAME;
        List<Map<String, Object>> quoteMaps = jdbcTemplate.queryForList(findAllQuery);

        LinkedList<Quote> foundQuotes = new LinkedList<>();
        for (Map<String, Object> map : quoteMaps)
            foundQuotes.add((Quote) quoteUtil.createEntity(map));

        return foundQuotes;
    }

    @Override
    public Optional<Quote> findById(String ticker) {

        String findByIdQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";
        List<Map<String, Object>> quoteRow = jdbcTemplate.queryForList(findByIdQuery, ticker);

        if (quoteRow.size() != 1)
            return Optional.empty();
        else
            return Optional.of((Quote) quoteUtil.createEntity(quoteRow.get(0)));
    }

    @Override
    public boolean existsById(String ticker) {
        return findById(ticker).isPresent();
    }

    @Override
    public void deleteById(String ticker) {

        String deleteByIdQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " = ?";

        jdbcTemplate.update(deleteByIdQuery, ticker);
    }

    @Override
    public long count() {

        String countQuery = "SELECT COUNT(*) FROM " + TABLE_NAME;
        long count;

        try {
            count = jdbcTemplate.queryForObject(countQuery, Long.class);
        } catch (NullPointerException e) {
            throw new DataRetrievalFailureException(FAILED_COUNT_QUERY_ERROR_MESSAGE);
        }

        return count;
    }

    @Override
    public void deleteAll() {

        String deleteAllQuery = "DELETE FROM " + TABLE_NAME;

        jdbcTemplate.update(deleteAllQuery);
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_ERROR_MESSAGE);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_ERROR_MESSAGE);
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_ERROR_MESSAGE);
    }
}
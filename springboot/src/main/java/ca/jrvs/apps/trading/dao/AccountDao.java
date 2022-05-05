package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.AccountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final String TABLE_NAME = "account";
    private final String ID_COLUMN = "id";
    private final String INSUFFICIENT_FUNDS_ERROR_MESSAGE = "Funds are insufficient for this " +
            "withdrawal attempt.";
    private final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Unsupported operation.";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);

        super.entityUtil = new AccountUtil(this);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<Account> getEntityClass() {
        return Account.class;
    }

    public Optional<Account> findByTraderId(Integer traderId) {

        List<Account> accountList = findAll().stream()
                .filter(account -> Objects.equals(account.getTrader_id(), traderId))
                .collect(Collectors.toList());

        if (accountList.size() != 1)
            return Optional.empty();
        else
            return Optional.of(accountList.get(0));
    }

    public Optional<Account> addAmountById(Integer accountId, Double funds) {

        String queryString = "SELECT amount FROM " + TABLE_NAME + " WHERE "
                + ID_COLUMN + " = " + accountId;

        Double previousFunds = jdbcTemplate.queryForObject(queryString, Double.class);
        Double nuFunds = previousFunds + funds;
        if (nuFunds <= 0)
            throw new IllegalArgumentException(INSUFFICIENT_FUNDS_ERROR_MESSAGE);

        String updateString = "UPDATE " + TABLE_NAME + " SET amount = " + nuFunds
                + " WHERE id = " + accountId;

        jdbcTemplate.update(updateString);

        return findById(accountId);
    }

    @Override
    public int updateOne(Account entity) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void delete(Account account) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void deleteAll(Iterable<? extends Account> iterable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }
}

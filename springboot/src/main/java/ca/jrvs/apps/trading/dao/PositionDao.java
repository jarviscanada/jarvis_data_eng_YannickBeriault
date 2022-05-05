package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao extends JdbcCrudDao<Position> {

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private final String TABLE_NAME = "position";
    private final String ID_COLUMN = "account_id";
    private final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Unsupported operation.";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
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
    Class<Position> getEntityClass() {
        return Position.class;
    }

    public int countPositionsOfAccount(int accountId) {

        int counter = 0;

        List<Position> allPositions = findAll();
        for (Position position : allPositions) {

            if (position.getAccount_id() == accountId)
                counter++;
        }

        return counter;
    }

    @Override
    public int updateOne(Position entity) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public <S extends Position> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void delete(Position position) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void deleteAll(Iterable<? extends Position> iterable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public <S extends Position> S save(S entity) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }
}

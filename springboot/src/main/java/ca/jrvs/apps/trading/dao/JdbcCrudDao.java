package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.EntityUtil;
import ca.jrvs.apps.trading.model.domain.QuoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.*;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

    private static final String CANT_FIND_ID_ERROR_MESSAGE = "Can't find Id: ";
    private static final String FAILED_COUNT_QUERY_ERROR_MESSAGE = "Unable to retrieve count " +
            "for %s (no number was returned).";
    private static final String UNABLE_TO_UPDATE_ERROR_MESSAGE = "Unable to update quote.";

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    private EntityUtil entityUtil = new QuoteUtil();

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdColumnName();

    abstract Class<T> getEntityClass();

    /**
     * Save an entity and update auto-generated integer ID
     * @param entity to be saved
     * @return saved entity
     */
    @Override
    public <S extends T> S save(S entity) {

        if (existsById(entity.getId())) {

            if (updateOne(entity) != 1)
                throw new DataRetrievalFailureException(UNABLE_TO_UPDATE_ERROR_MESSAGE);
        } else
            addOne(entity);

        return entity;
    }

    /**
     * Helper method that saves one quote
     */
    private <S extends T> void addOne(S entity) {

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

        Number nuId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(nuId.intValue());
    }

    /**
     * Helper method that updates one quote
     */
    abstract public int updateOne(T entity);

    @Override
    public Optional<T> findById(Integer id) {

        Optional<T> entity = Optional.empty();
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE "
                + getIdColumnName() + " = ?";

        try {

            entity = Optional.ofNullable((T) getJdbcTemplate()
                    .queryForObject(selectSql,
                            BeanPropertyRowMapper.newInstance(getEntityClass()), id)
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug(CANT_FIND_ID_ERROR_MESSAGE + id, e);
        }

        return entity;
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<T> findAll() {

        String findAllQuery = "SELECT * FROM " + getTableName();
        List<Map<String, Object>> entityMaps = getJdbcTemplate().queryForList(findAllQuery);

        LinkedList<T> foundEntities = new LinkedList<>();
        for (Map<String, Object> map : entityMaps) {

            foundEntities.add(getEntityClass()
                    .cast(entityUtil.createEntity(map))
            );
        }

        return foundEntities;
    }

    @Override
    public List<T> findAllById(Iterable<Integer> ids) {

        List<T> foundEntities = new LinkedList<>();

        for (Integer id : ids) {

            Optional<T> possibleEntity = findById(id);

            if (possibleEntity.isPresent())
                foundEntities.add(possibleEntity.get());
            else
                throw new IllegalArgumentException(CANT_FIND_ID_ERROR_MESSAGE + id);
        }

        return foundEntities;
    }

    @Override
    public void deleteById(Integer id) {

        String deleteByIdQuery = "DELETE FROM " + getTableName()
                + " WHERE " + getIdColumnName() + " = ?";

        getJdbcTemplate().update(deleteByIdQuery, id);
    }

    @Override
    public long count() {

        String countQuery = "SELECT COUNT(*) FROM " + getTableName();
        long count;

        try {
            count = getJdbcTemplate().queryForObject(countQuery, Long.class);
        } catch (NullPointerException e) {
            throw new DataRetrievalFailureException(String
                    .format(FAILED_COUNT_QUERY_ERROR_MESSAGE, getEntityClass().toString())
            );
        }

        return count;
    }

    @Override
    public void deleteAll() {

        String deleteAllQuery = "DELETE FROM " + getTableName();

        getJdbcTemplate().update(deleteAllQuery);
    }
}
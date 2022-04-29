package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

    private static final Logger logger = LoggerFactory.getLogger(SecurityOrder.class);

    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";
    private final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Unsupported operation.";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
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
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    public List<SecurityOrder> getSecurityOrdersOfAccount(int accountId) {

        List<SecurityOrder> securityOrdersOfAccount = new LinkedList<>();
        List<SecurityOrder> allOrders = findAll();

        for (SecurityOrder order : allOrders) {

            if (order.getAccount_id() == accountId)
                securityOrdersOfAccount.add(order);
        }

        return securityOrdersOfAccount;
    }

    @Override
    public int updateOne(SecurityOrder entity) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public <S extends SecurityOrder> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void delete(SecurityOrder securityOrder) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public void deleteAll(Iterable<? extends SecurityOrder> ordersToDelete) {

        StringBuilder stringBuilder = new StringBuilder("DELETE FROM " + TABLE_NAME
                + "WHERE id IN (");

        Iterator<SecurityOrder> iterator =
                (Iterator<SecurityOrder>) ordersToDelete.iterator();
        if (iterator.hasNext())
            stringBuilder.append(iterator.next().getId());

        while (iterator.hasNext()) {

            stringBuilder.append(", ");
            stringBuilder.append(iterator.next().getId());
        }

        stringBuilder.append(")");

        jdbcTemplate.update(stringBuilder.toString());
    }

}

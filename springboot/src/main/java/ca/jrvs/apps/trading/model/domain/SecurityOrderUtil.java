package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.dao.AccountDao;

import javax.json.JsonObject;
import java.util.Map;

public class SecurityOrderUtil extends EntityUtil {

    private final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Unsupported operation.";

    @Override
    public Entity<?> createEntity(JsonObject jsonObject) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_ERROR_MESSAGE);
    }

    @Override
    public Entity<?> createEntity(Map<String, Object> quoteMap) {

        SecurityOrder nuSecurityOrder = new SecurityOrder();

        nuSecurityOrder.setId((int) quoteMap.get("id"));
        nuSecurityOrder.setAccount_id((int) quoteMap.get("account_id"));
        nuSecurityOrder.setStatus((String) quoteMap.get("status"));
        nuSecurityOrder.setTicker((String) quoteMap.get("ticker"));
        nuSecurityOrder.setSize((int) quoteMap.get("size"));
        nuSecurityOrder.setPrice((double) quoteMap.get("price"));
        nuSecurityOrder.setNotes((String) quoteMap.get("notes"));

        return nuSecurityOrder;
    }
}

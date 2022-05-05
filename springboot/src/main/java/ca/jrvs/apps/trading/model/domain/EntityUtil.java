package ca.jrvs.apps.trading.model.domain;

import javax.json.JsonObject;
import java.util.Map;

public abstract class EntityUtil {

    public abstract Entity<?> createEntity(JsonObject jsonObject);

    public abstract Entity<?> createEntity(Map<String, Object> quoteMap);
}
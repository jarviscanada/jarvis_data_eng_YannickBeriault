package ca.jrvs.apps.trading.model.domain;

import javax.json.JsonObject;
import java.util.Map;

public class QuoteUtil extends EntityUtil {

    public Entity<String> createEntity(JsonObject jsonObject) {

        Quote nuQuote = new Quote();

        nuQuote.setId(jsonObject.getString("symbol"));
        nuQuote.setLatestPrice(jsonObject.getJsonNumber("latestPrice").doubleValue());
        nuQuote.setBidPrice(jsonObject.getJsonNumber("iexBidPrice").doubleValue());
        nuQuote.setBidSize(jsonObject.getInt("iexBidSize"));
        nuQuote.setAskPrice(jsonObject.getJsonNumber("iexAskPrice").doubleValue());
        nuQuote.setAskSize(jsonObject.getInt("iexAskSize"));

        return nuQuote;
    }

    public Entity<String> createEntity(Map<String, Object> quoteMap) {

        Quote nuQuote = new Quote();

        nuQuote.setId((String) quoteMap.get("ticker"));
        nuQuote.setLatestPrice((double) quoteMap.get("latest_price"));
        nuQuote.setBidPrice((double) quoteMap.get("bid_price"));
        nuQuote.setBidSize((int) quoteMap.get("bid_size"));
        nuQuote.setAskPrice((double) quoteMap.get("ask_price"));
        nuQuote.setAskSize((int) quoteMap.get("ask_size"));

        return nuQuote;
    }

    public static Quote createEntity(IexQuote iexQuote) {

        Quote nuQuote = new Quote();
        Double latestPrice = iexQuote.getLatestPrice();

        nuQuote.setId(iexQuote.getId());
        nuQuote.setLatestPrice(latestPrice == null ? 0 : latestPrice);
        nuQuote.setBidPrice(iexQuote.getBidPrice());
        nuQuote.setBidSize(iexQuote.getBidSize());
        nuQuote.setAskPrice(iexQuote.getAskPrice());
        nuQuote.setAskSize(iexQuote.getAskSize());

        return nuQuote;
    }
}
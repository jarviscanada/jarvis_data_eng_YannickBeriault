package ca.jrvs.apps.trading.model.domain;

import javax.json.JsonObject;
import java.util.Map;

public class QuoteUtil {

    public static Quote createQuote(JsonObject jsonObject) {

        Quote nuQuote = new Quote();

        nuQuote.setId(jsonObject.getString("symbol"));
        nuQuote.setLastPrice(jsonObject.getJsonNumber("latestPrice").doubleValue());
        nuQuote.setBidPrice(jsonObject.getJsonNumber("iexBidPrice").doubleValue());
        nuQuote.setBidSize(jsonObject.getInt("iexBidSize"));
        nuQuote.setAskPrice(jsonObject.getJsonNumber("iexAskPrice").doubleValue());
        nuQuote.setAskSize(jsonObject.getInt("iexAskSize"));

        return nuQuote;
    }

    public static Quote createQuote(Map<String, Object> quoteMap) {

        Quote nuQuote = new Quote();

        nuQuote.setId((String) quoteMap.get("ticker"));
        nuQuote.setLastPrice((double) quoteMap.get("last_price"));
        nuQuote.setBidPrice((double) quoteMap.get("bid_price"));
        nuQuote.setBidSize((int) quoteMap.get("bid_size"));
        nuQuote.setAskPrice((double) quoteMap.get("ask_price"));
        nuQuote.setAskSize((int) quoteMap.get("ask_size"));

        return nuQuote;
    }
}
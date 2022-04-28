package ca.jrvs.apps.trading.model.domain;

import javax.json.JsonObject;
import java.util.Map;

public class IexQuoteUtil {

    public static IexQuote createIexQuote(JsonObject jsonObject) {

        IexQuote nuQuote = new IexQuote();

        nuQuote.setId(jsonObject.getString("symbol"));
        nuQuote.setLatestPrice(jsonObject.getJsonNumber("latestPrice").doubleValue());
        nuQuote.setBidPrice(jsonObject.getJsonNumber("iexBidPrice").doubleValue());
        nuQuote.setBidSize(jsonObject.getInt("iexBidSize"));
        nuQuote.setAskPrice(jsonObject.getJsonNumber("iexAskPrice").doubleValue());
        nuQuote.setAskSize(jsonObject.getInt("iexAskSize"));

        return nuQuote;
    }

    public static IexQuote createIexQuote(Map<String, Object> quoteMap) {

        IexQuote nuQuote = new IexQuote();

        nuQuote.setId((String) quoteMap.get("ticker"));
        nuQuote.setLatestPrice((double) quoteMap.get("latest_price"));
        nuQuote.setBidPrice((double) quoteMap.get("bid_price"));
        nuQuote.setBidSize((int) quoteMap.get("bid_size"));
        nuQuote.setAskPrice((double) quoteMap.get("ask_price"));
        nuQuote.setAskSize((int) quoteMap.get("ask_size"));

        return nuQuote;
    }
}
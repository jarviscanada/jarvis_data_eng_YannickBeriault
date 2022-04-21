package ca.jrvs.apps.trading.model.domain;

import javax.json.JsonObject;

public class IexQuoteUtil {

    public static IexQuote createIexQuote(JsonObject jsonObject) {

        IexQuote nuQuote = new IexQuote();

        nuQuote.setTicker(jsonObject.getString("symbol"));
        nuQuote.setLastPrice(jsonObject.getJsonNumber("latestPrice").doubleValue());
        nuQuote.setBidPrice(jsonObject.getJsonNumber("iexBidPrice").doubleValue());
        nuQuote.setBidSize(jsonObject.getInt("iexBidSize"));
        nuQuote.setAskPrice(jsonObject.getJsonNumber("iexAskPrice").doubleValue());
        nuQuote.setAskSize(jsonObject.getInt("iexAskSize"));

        return nuQuote;
    }
}
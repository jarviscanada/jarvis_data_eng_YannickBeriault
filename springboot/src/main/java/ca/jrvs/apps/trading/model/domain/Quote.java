package ca.jrvs.apps.trading.model.domain;

/**
 * https://iexcloud.io/docs/api/#quote
 */

public class Quote implements Entity<String> {

    private String ticker;
    private double last_price;
    private double bid_price;
    private int bid_size;
    private double ask_price;
    private int ask_size;

    @Override
    public String getId() {
        return ticker;
    }

    @Override
    public void setId(String ticker) {
        this.ticker = ticker;
    }

    public double getLastPrice() {
        return last_price;
    }

    public void setLastPrice(double lastPrice) {
        this.last_price = lastPrice;
    }

    public double getBidPrice() {
        return bid_price;
    }

    public void setBidPrice(double bidPrice) {
        this.bid_price = bidPrice;
    }

    public int getBidSize() {
        return bid_size;
    }

    public void setBidSize(int bidSize) {
        this.bid_size = bidSize;
    }

    public double getAskPrice() {
        return ask_price;
    }

    public void setAskPrice(double askPrice) {
        this.ask_price = askPrice;
    }

    public int getAskSize() {
        return ask_size;
    }

    public void setAskSize(int askSize) {
        this.ask_size = askSize;
    }
}
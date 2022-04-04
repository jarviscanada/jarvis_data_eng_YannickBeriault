package ca.jrvs.apps.twitter.model;

import javax.json.JsonObject;
import java.util.Date;

public class Tweet {

    private String createdAt;
    private final ID id;
    private String text;
    private Entities entities;
    private Coordinates coordinates;
    private int retweetCount;
    private int favoriteCount;
    private boolean favorited;
    private boolean retweeted;

    public Tweet(String id, String text) {

        this.id = new ID(id);
        this.text = text;
    }

    public Tweet(JsonObject jsonObject) {

        this.createdAt = jsonObject.getString("created_at");
        this.id = new ID(jsonObject.getString("id"));
        this.text = jsonObject.getString("text");
        this.retweetCount = jsonObject.getInt("retweet_count");
        this.favoriteCount = jsonObject.getInt("favorite_count");
        this.favorited = jsonObject.getBoolean("favorited");
        this.retweeted = jsonObject.getBoolean("retweeted");
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id.getLongId();
    }

    public String getIdString() {
        return id.getIdString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }
}
package ca.jrvs.apps.twitter.model;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class Tweet {

    private String createdAt;
    private ID id;
    private String text;
    private Entities entities;
    private Coordinates coordinates;
    private int retweetCount;
    private int favoriteCount;
    private boolean favorited;
    private boolean retweeted;

    public Tweet(String text) {
        this.text = text;
    }

    public Tweet(String id, String text) {

        this.id = new ID(id);
        this.text = text;
    }

    public Tweet(String text, float longitude, float latitude) {

        this.text = text;
        this.coordinates = new Coordinates(longitude, latitude);
    }

    public Tweet(JsonObject jsonObject) {

        this.createdAt = jsonObject.getString("created_at");
        this.id = new ID(jsonObject.getString("id_str"));
        this.text = jsonObject.getString("text");
        this.retweetCount = jsonObject.getInt("retweet_count");
        this.favoriteCount = jsonObject.getInt("favorite_count");
        this.favorited = jsonObject.getBoolean("favorited");
        this.retweeted = jsonObject.getBoolean("retweeted");

        if (!jsonObject.isNull("coordinates")) {

            JsonArray coordinatesArray = jsonObject.getJsonArray("coordinates");
            this.coordinates = new Coordinates((float) coordinatesArray.getJsonNumber(1).doubleValue(),
                    (float) coordinatesArray.getJsonNumber(0).doubleValue());
        }

        this.entities = new Entities();
        JsonArray hashtagsJsonArray = jsonObject.getJsonObject("entities").getJsonArray("hashtags");
        if (hashtagsJsonArray.size() > 0)
            this.entities.setHashtags(hashtagsParser(hashtagsJsonArray));

        JsonArray userMentionsJsonArray = jsonObject.getJsonObject("entities").getJsonArray("user_mentions");
        if (userMentionsJsonArray.size() > 0)
            this.entities.setUserMentions(userMentionsParser(userMentionsJsonArray));
    }

    private Hashtag[] hashtagsParser(JsonArray jsonArray) {

        int size = jsonArray.size();
        Hashtag[] hashtags = new Hashtag[size];

        for(int i = 0; i < size; i++) {

            JsonArray indicesArray = jsonArray.getJsonObject(i).getJsonArray("indices");
            hashtags[i] = new Hashtag(new int[] {indicesArray.getInt(0),
                    indicesArray.getInt(1)},
                    jsonArray.getJsonObject(i).getString("text"));
        }

        return hashtags;
    }

    private UserMention[] userMentionsParser(JsonArray jsonArray) {

        int size = jsonArray.size();
        UserMention[] userMentions = new UserMention[size];

        for (int i = 0; i < size; i++) {

            JsonObject currentObject = jsonArray.getJsonObject(i);
            UserMention currentUserMention = new UserMention(currentObject.getString("id_str"),
                    currentObject.getString("name"), currentObject.getString("screen_name"));
            JsonArray indicesArray = jsonArray.getJsonObject(i).getJsonArray("indices");
            currentUserMention.setIndices(new int[] {indicesArray.getInt(0),
                    indicesArray.getInt(1)});
            userMentions[i] = currentUserMention;
        }

        return userMentions;
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

    public void setId(String idString) {
        this.id = new ID(idString);
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
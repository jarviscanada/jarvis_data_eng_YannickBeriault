package ca.jrvs.apps.twitter.model;

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

    private String[] wantedFields;

    public Tweet() {
        this.entities = new Entities();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Entities getEntities() {
        return this.entities;
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

    public void setHashtags(Hashtag[] hashtagsArray) {
        this.entities.setHashtags(hashtagsArray);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinates(float longitude, float latitude) {
        this.coordinates = new Coordinates(longitude, latitude);
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

    public void setUserMentions(UserMention[] userMentionsArray) {
        this.entities.setUserMentions(userMentionsArray);
    }

    public String[] getWantedFields() {
        return wantedFields;
    }

    public void setWantedFields(String[] wantedFields) {
        this.wantedFields = wantedFields;
    }
}
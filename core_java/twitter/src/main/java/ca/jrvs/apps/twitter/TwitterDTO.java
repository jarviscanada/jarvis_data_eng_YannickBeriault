package ca.jrvs.apps.twitter;

import java.util.Date;
import java.util.Random;

public class TwitterDTO {

    private Date createdAt;
    private long id;
    private String text;
    private String[] hashtags;
    private String[] mentions;
    private float[] coordinates;
    private int retweetCount;
    private int favoriteCount;
    private boolean favorited;
    private boolean retweeted;

    private static long incrementableIdMaker = 496489496856468L;

    public TwitterDTO() {

        this.createdAt = new Date();
        this.id = incrementableIdMaker++;
        this.text = "Ce tweet est un dummy.";
        this.hashtags = new String[0];
        this.mentions = new String[0];
        this.coordinates = new float[]{43.443f, 79.2224f};
        this.retweetCount = 0;
        this.favoriteCount = 0;
        this.favorited = false;
        this.retweeted = false;
    }

    public TwitterDTO(String text, float[] coordinates) {

        Random randomizer = new Random();

        this.createdAt = new Date();
        this.id = randomizer.nextLong();
        this.text = text;
        this.hashtags = new String[0];
        this.mentions = new String[0];
        this.coordinates = coordinates;
        this.retweetCount = 0;
        this.favoriteCount = 0;
        this.favorited = false;
        this.retweeted = false;
    }

    public static void setIncrementableIdMaker(long idMaker) {
        incrementableIdMaker = idMaker;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(String[] hashtags) {
        this.hashtags = hashtags;
    }

    public String[] getMentions() {
        return mentions;
    }

    public void setMentions(String[] mentions) {
        this.mentions = mentions;
    }

    public float getLatitude() {
        return getCoordinates()[0];
    }

    public float getLongitude() {
        return getCoordinates()[1];
    }

    private float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
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

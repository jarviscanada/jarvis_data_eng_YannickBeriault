package ca.jrvs.apps.twitter.model;

import java.util.Date;

public class Tweet {

    private Date createdAt;
    private long id;
    private String idString;
    private String text;
    private Entities entities;
    private Coordinates coordinates;
    private int retweetCount;
    private int favoriteCount;
    private boolean favorited;
    private boolean retweeted;

    public Tweet() {

    }
}
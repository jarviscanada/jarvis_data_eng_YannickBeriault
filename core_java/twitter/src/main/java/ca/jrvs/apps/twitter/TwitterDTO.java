package ca.jrvs.apps.twitter;

import java.util.Date;

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
}

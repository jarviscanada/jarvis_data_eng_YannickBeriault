package ca.jrvs.apps.twitter.model;

import org.apache.catalina.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Entities {

    private Hashtag[] hashtags;
    private UserMention[] userMentions;

    public Hashtag[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(Hashtag[] hashtags) {
        this.hashtags = hashtags;
    }

    public UserMention[] getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(UserMention[] userMentions) {
        this.userMentions = userMentions;
    }
}

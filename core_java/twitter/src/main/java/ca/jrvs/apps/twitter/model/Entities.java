package ca.jrvs.apps.twitter.model;

public class Entities {

    private Hashtag[] hashtags;
    private UserMention[] userMentions;

    public Entities() {

        hashtags = new Hashtag[0];
        userMentions = new UserMention[0];
    }

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

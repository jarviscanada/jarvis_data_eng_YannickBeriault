package ca.jrvs.apps.twitter.dao;

public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(String tweetNotFoundExceptionMessage) {
        super(tweetNotFoundExceptionMessage);
    }
}

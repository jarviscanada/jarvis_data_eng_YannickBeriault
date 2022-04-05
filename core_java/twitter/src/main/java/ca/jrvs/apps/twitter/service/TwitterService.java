package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.List;

public class TwitterService implements Service {

    private CrdDao dao;

    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {

        validatePostTweet(tweet);

        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) {

        if (tweet.getText().length() > 140)
            throw new IllegalArgumentException("Tweet text needs to be 140 characters or less.");

        Coordinates coordinates = tweet.getCoordinates();
        if (tweet.getCoordinates() != null) {

            if (Math.abs(coordinates.getLongitude()) > 180.0f
                    || Math.abs(coordinates.getLatitude()) > 90.0f)
                throw new IllegalArgumentException("GeoTag was not valid.");
        }
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {

        validateShowTweet(id, fields);

        return null;
    }

    private void validateShowTweet(String id, String[] fields) {

    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        return null;
    }
}

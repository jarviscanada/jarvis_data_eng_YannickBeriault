package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetUtil;

import java.util.*;

public class TwitterService implements Service {

    private static final TreeSet<String> VALID_FIELDS_TREE = new TreeSet<>(TweetUtil.VALID_FIELDS);
    private static final String INVALID_ID_ERROR_MESSAGE = "Id is not a valid positive long form integer.";

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

        Tweet returnedTweet = (Tweet) dao.findById(id);
        returnedTweet.setWantedFields(fields);

        return returnedTweet;
    }

    private void validateShowTweet(String id, String[] fields) {

        validateId(id);
        validateFields(fields);
    }

    private void validateId(String id) {

        long idLong;

        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ID_ERROR_MESSAGE);
        }

        if (idLong < 0)
            throw new IllegalArgumentException(INVALID_ID_ERROR_MESSAGE);
    }

    private void validateFields(String[] fields) {

        for (String field : fields) {

            if (!VALID_FIELDS_TREE.contains(field))
                throw new IllegalArgumentException("An invalid field was queried.");
        }
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {

        for (String id : ids)
            validateId(id);

        LinkedList<Tweet> deletedTweets = new LinkedList<>();

        //Here, we iterate two times to ensure the atomicity of the transaction: if any single id is invalid, no
        // tweet will be deleted.
        for (String id : ids)
            deletedTweets.add((Tweet) dao.deleteById(id));

        return deletedTweets;
    }
}

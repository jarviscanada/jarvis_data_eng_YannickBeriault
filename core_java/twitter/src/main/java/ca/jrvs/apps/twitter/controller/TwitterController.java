package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;

import java.util.LinkedList;
import java.util.List;

public class TwitterController implements Controller {

    private static final String INCORRECT_NUMBER_OF_ARGS_ERROR = "Number or arguments is incorrect.";
    private static final String GEO_TAG_ERROR_MESSAGE = "Geo tag is in wrong format.";
    private static final String COORDINATES_SEPARATOR = ":";
    private static final String IDS_AND_FIELDS_SEPARATOR = ",";

    private Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) {

        validateNumberOfArgs(args);

        if (args.length == 3) {

            float[] longitudeLatitude = parseAndValidateCoordinates(args[2]);
            return (Tweet) service.postTweet(new Tweet(args[1], longitudeLatitude[0], longitudeLatitude[1]));
        } else
            return (Tweet) service.postTweet(new Tweet(args[1]));
    }

    private float[] parseAndValidateCoordinates(String coordinatesCandidates) {

        float[] longitudeLatitude = new float[2];
        String[] toParse = coordinatesCandidates.split(COORDINATES_SEPARATOR);
        if (toParse.length != 2)
            throw new IllegalArgumentException(GEO_TAG_ERROR_MESSAGE);

        try {
            for (int i = 0; i < 2; i++)
                longitudeLatitude[i] = Float.parseFloat(toParse[i]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(GEO_TAG_ERROR_MESSAGE);
        }

        return longitudeLatitude;
    }

    @Override
    public Tweet showTweet(String[] args) {

        validateNumberOfArgs(args);

        String[] fields;
        if (args.length == 3)
            fields = parseCommaSeparatedString(args[2]);
        else
            fields = new String[0];

        return (Tweet) service.showTweet(args[1], fields);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {

        validateNumberOfArgs(args);

        String[] ids;
        if (args.length == 2)
            ids = parseCommaSeparatedString(args[1]);
        else
            throw new IllegalArgumentException(INCORRECT_NUMBER_OF_ARGS_ERROR);

        return service.deleteTweets(ids);
    }

    private void validateNumberOfArgs(String[] args) {

        if (args.length > 3)
            throw new IllegalArgumentException(INCORRECT_NUMBER_OF_ARGS_ERROR);
    }

    private String[] parseCommaSeparatedString(String toParse) {

        return toParse.split(IDS_AND_FIELDS_SEPARATOR);
    }
}

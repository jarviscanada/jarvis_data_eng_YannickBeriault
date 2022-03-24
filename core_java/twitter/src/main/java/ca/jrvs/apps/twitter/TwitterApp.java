package ca.jrvs.apps.twitter;

import java.util.zip.DataFormatException;

public class TwitterApp {

    final static String ARGUMENTS_NUMBER_ERROR_MESSAGE = "Please provide the right number of arguments.";
    final static String GEO_TAG_ERROR_MESSAGE = "Geo tag is in wrong format.";
    final static String ID_FORMAT_ERROR_MESSAGE = "Id is not in correct format.";
    final static String OPTION_ARGUMENT_ERROR_MESSAGE = "Incorrect option argument.";
    final static String TEXT_LENGTH_ERROR_MESSAGE = "Tweet must be between 1 and 140 characters.";
    final static String USAGE_ERROR_MESSAGE = "Usage\n" +
            "TwitterApp post|show|delete \"tweet_text\" \"latitude:longitude\"\n" +
            "\nArguments:\n" +
            "tweet_text         - tweet_text cannot exceed 140 UTF-8 encoded characters. \n" +
            "latitude:longitude - Geo location.";

    public Tweeter tweeter = new Tweeter();

    //Methods to manage argument errors
    private static void argumentErrorManager(String specificMessage) {
        System.out.println(specificMessage + "\n\n" + USAGE_ERROR_MESSAGE);
    }

    static boolean checkArgsLength(String[] arguments) {
        return arguments.length == 3
                || (arguments.length == 2 && arguments[0].equalsIgnoreCase("show"));
    }

    static boolean checkOptionArgument(String option) {
        return option.equalsIgnoreCase("post")
                || option.equalsIgnoreCase("show")
                || option.equalsIgnoreCase("delete");
    }

    //Methods for the processing of commands.
    void methodSwitch(String[] arguments) throws DataFormatException {

        if (arguments[0].equalsIgnoreCase("post"))
            post(arguments[1], arguments[2]);
        else if (arguments[0].equalsIgnoreCase("show"))
            show(arguments[1], arguments.length == 3 ? arguments[2] : null);
        //else if (arguments[0].equalsIgnoreCase("delete"))

    }

    private void post(String tweetText, String geoTag) throws DataFormatException {

        if (tweetText.length() < 1 || tweetText.length() > 140)
            throw new DataFormatException(TEXT_LENGTH_ERROR_MESSAGE);

        float[] latitudeLongitude = geoTagParser(geoTag);

        this.tweeter.post(tweetText, latitudeLongitude);
    }

    private void show(String idString, String optionsString) throws DataFormatException {

        long id;
        String[] options;

        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw new DataFormatException(ID_FORMAT_ERROR_MESSAGE);
        }

        if (optionsString == null)
            options = new String[0];
        else {
            options = optionsString.split(",");
        }

        System.out.println(this.tweeter.show(id, options));
    }

    static float[] geoTagParser(String geoTag) throws DataFormatException {

        float[] latitudeLongitude = new float[2];
        String[] toParse = geoTag.split(":");
        if (toParse.length != 2)
            throw new DataFormatException(GEO_TAG_ERROR_MESSAGE);

        try {
            for (int i = 0; i < 2; i++)
                latitudeLongitude[i] = Float.parseFloat(toParse[i]);
        } catch (NumberFormatException e) {
            throw new DataFormatException(GEO_TAG_ERROR_MESSAGE);
        }

        if (latitudeLongitude[0] > 90.0 || latitudeLongitude[1] > 180.0)
            throw new DataFormatException(GEO_TAG_ERROR_MESSAGE);

        return latitudeLongitude;
    }


    public static void main(String[] args) {

        if (!checkArgsLength(args)) {

            argumentErrorManager(ARGUMENTS_NUMBER_ERROR_MESSAGE);
            return;
        }

        if (!checkOptionArgument(args[0])) {

            argumentErrorManager(OPTION_ARGUMENT_ERROR_MESSAGE);
            return;
        }

        TwitterApp twitterApp = new TwitterApp();
        try {
            twitterApp.methodSwitch(args);
        } catch (DataFormatException e) {
            argumentErrorManager(e.getMessage());
        }
    }
}
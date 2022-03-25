package ca.jrvs.apps.twitter;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

public class Tweeter {

    private final String INVALID_SHOW_OPTION_ERROR_MESSAGE = "One of the fields name entered is not valid.";
    private final String UNEXISTING_TWEET_ERROR_MESSAGE = "Id entered does not correspond to any tweet in archive";

    public TweetBankMock tweetBank;

    public Tweeter() {
        this.tweetBank = new TweetBankMock();
    }

    public void post(String tweetText, float[] latitudeLongitude) {

        TwitterDTO tweetToPost = new TwitterDTO(tweetText, latitudeLongitude);
        this.tweetBank.put(tweetToPost);

        JsonObject jsonObject = buildJsonObject(this.tweetBank.getTweet(tweetBank.getAnyId()));
        System.out.println(createStringWriter(jsonObject));
    }

    public StringWriter show(long id, String[] options) throws DataFormatException {

        TwitterDTO tweet = tweetBank.getTweet(id);

        if (tweet == null)
            throw new DataFormatException(UNEXISTING_TWEET_ERROR_MESSAGE);

        JsonObject jsonObject = buildJsonObject(tweet);

        if (options.length != 0)
            jsonObject = shortenJsonObject(jsonObject, options);

        return createStringWriter(jsonObject);
    }

    private JsonObject buildJsonObject(TwitterDTO tweet) {

        JsonArrayBuilder hashtagsArrayBuilder = Json.createArrayBuilder();
        for (String h : tweet.getHashtags())
            hashtagsArrayBuilder.add(h);
        JsonArrayBuilder userMentionsArrayBuilder = Json.createArrayBuilder();
        for (String u : tweet.getMentions())
            userMentionsArrayBuilder.add(u);

        return Json.createObjectBuilder()
                .add("created_at", tweet.getCreatedAt().toString())
                .add("id", tweet.getId())
                .add("id_str", Long.toString(tweet.getId()))
                .add("text", tweet.getText())
                .add("entities", Json.createObjectBuilder()
                        .add("hashtags", hashtagsArrayBuilder.build())
                        .add("user_mentions", userMentionsArrayBuilder.build())
                        .build())
                .add("coordinates", Json.createObjectBuilder()
                        .add("coordinates", Json.createArrayBuilder()
                                .add(tweet.getLatitude())
                                .add(tweet.getLongitude())
                                .build())
                        .add("type", "Point")
                        .build())
                .add("retweet_count", tweet.getRetweetCount())
                .add("favorite_count", tweet.getFavoriteCount())
                .add("favorited", tweet.isFavorited())
                .add("retweeted", tweet.isRetweeted())
                .build();
    }

    private JsonObject shortenJsonObject(JsonObject jsonObject, String[] options) throws DataFormatException {

        JsonObjectBuilder shortenedJsonObjectBuilder = Json.createObjectBuilder();
        Set<String> keys = jsonObject.keySet();
        HashMap<String, String> subKeys = new HashMap<>();

        for (String key : keys) {

            if (jsonObject.get(key) instanceof JsonObject) {

                for (String subKey : ((JsonObject) jsonObject.get(key)).keySet())
                    subKeys.put(subKey, key);
            }
        }

        for (String option : options) {

            if (!keys.contains(option)) {

                if (!subKeys.containsKey(option))
                    throw new DataFormatException(INVALID_SHOW_OPTION_ERROR_MESSAGE);
                else {

                    JsonObject subJsonObject = (JsonObject) jsonObject.get(subKeys.get(option));
                    shortenedJsonObjectBuilder.add(option, subJsonObject.get(option));
                }
            } else
                shortenedJsonObjectBuilder.add(option, jsonObject.get(option));
        }

        return shortenedJsonObjectBuilder.build();
    }

    private StringWriter createStringWriter(JsonObject jsonObject) {

        StringWriter prettyWriter = new StringWriter();

        Map<String, Object> map = new HashMap<>();
        map.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(map);
        JsonWriter jsonWriter = writerFactory.createWriter(prettyWriter);
        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();

        return prettyWriter;
    }
}
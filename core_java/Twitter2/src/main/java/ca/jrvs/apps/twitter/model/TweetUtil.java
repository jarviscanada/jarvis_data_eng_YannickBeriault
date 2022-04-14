package ca.jrvs.apps.twitter.model;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

public class TweetUtil {

    public static final ArrayList<String> VALID_FIELDS = new ArrayList<>(Arrays.asList("created_at", "id",
            "id_str", "text", "entities", "hashtags", "user_mentions", "coordinates", "retweet_count",
            "favorite_count", "favorited", "retweeted"));

    public static Tweet createTweet(String text) {

        Tweet nuTweet = new Tweet();
        nuTweet.setText(text);

        return nuTweet;
    }

    public static Tweet createTweet(String id, String text) {

        Tweet nuTweet = new Tweet();
        nuTweet.setId(id);
        nuTweet.setText(text);

        return nuTweet;
    }

    public static Tweet createTweet(String text, float longitude, float latitude) {

        Tweet nuTweet = new Tweet();
        nuTweet.setText(text);
        nuTweet.setCoordinates(longitude, latitude);

        return nuTweet;
    }

    public static Tweet createTweet(JsonObject jsonObject) {

        Tweet nuTweet = new Tweet();

        nuTweet.setCreatedAt(jsonObject.getString("created_at"));
        nuTweet.setId(jsonObject.getString("id_str"));
        nuTweet.setText(jsonObject.getString("text"));
        nuTweet.setRetweetCount(jsonObject.getInt("retweet_count"));
        nuTweet.setFavoriteCount(jsonObject.getInt("favorite_count"));
        nuTweet.setFavorited(jsonObject.getBoolean("favorited"));
        nuTweet.setRetweeted(jsonObject.getBoolean("retweeted"));

        if (!jsonObject.isNull("coordinates")) {

            JsonArray coordinatesArray = jsonObject.getJsonObject("coordinates")
                    .getJsonArray("coordinates");
            nuTweet.setCoordinates((float) coordinatesArray.getJsonNumber(1).doubleValue(),
                    (float) coordinatesArray.getJsonNumber(0).doubleValue());
        }

        JsonArray hashtagsJsonArray = jsonObject.getJsonObject("entities").getJsonArray("hashtags");
        if (hashtagsJsonArray.size() > 0)
            nuTweet.setHashtags(jsonArrayIntoHashtagsArray(hashtagsJsonArray));

        JsonArray userMentionsJsonArray = jsonObject.getJsonObject("entities").getJsonArray("user_mentions");
        if (userMentionsJsonArray.size() > 0)
            nuTweet.setUserMentions(jsonArrayIntoUserMentionsArray(userMentionsJsonArray));

        return nuTweet;
    }

    private static Hashtag[] jsonArrayIntoHashtagsArray(JsonArray jsonArray) {

        Hashtag[] hashtagsArray = new Hashtag[jsonArray.size()];
        int index = 0;

        for (JsonValue jValue : jsonArray) {

            JsonObject jObject = (JsonObject) jValue;
            int[] indices = {jObject.getJsonArray("indices").getInt(0),
                    jObject.getJsonArray("indices").getInt(1)};

            hashtagsArray[index] = new Hashtag(indices, jObject.getString("text"));
            index++;
        }

        return hashtagsArray;
    }

    private static UserMention[] jsonArrayIntoUserMentionsArray(JsonArray jsonArray) {

        UserMention[] userMentionsArray = new UserMention[jsonArray.size()];
        int index = 0;

        for (JsonValue jValue : jsonArray) {

            JsonObject jObject = (JsonObject) jValue;
            int[] indices = {jObject.getJsonArray("indices").getInt(0),
                    jObject.getJsonArray("indices").getInt(1)};

            userMentionsArray[index] = new UserMention(jObject.getString("id_str"),
                    jObject.getString("name"), jObject.getString("screen_name"));
            userMentionsArray[index].setIndices(indices);
        }

        return userMentionsArray;
    }

    public static void printTweet(Tweet tweet) {

        TreeSet<String> selectedFields;
        boolean selectFields = tweet.getWantedFields().length > 0;
        boolean entitiesIsPresent = false;

        if (selectFields) {

            selectedFields = new TreeSet<>(Arrays.asList(tweet.getWantedFields()));
            tweet.setWantedFields(null);
        } else
            selectedFields = new TreeSet<>();

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        if (!selectFields || selectedFields.contains("created_at"))
            jsonObjectBuilder.add("created_at", tweet.getCreatedAt());
        if (!selectFields || selectedFields.contains("id"))
            jsonObjectBuilder.add("id", tweet.getId());
        if (!selectFields || selectedFields.contains("id_str"))
            jsonObjectBuilder.add("id_str", tweet.getIdString());
        if (!selectFields || selectedFields.contains("text"))
            jsonObjectBuilder.add("text", tweet.getText());
        if (!selectFields || selectedFields.contains("entities")) {

            JsonObjectBuilder entitiesJsonObjectBuilder = Json.createObjectBuilder();
            entitiesJsonObjectBuilder.add("hashtags",
                            produceHashtagsJsonArray(tweet.getEntities().getHashtags()))
                    .add("user_mentions",
                            produceUserMentionsJsonArray(tweet.getEntities().getUserMentions()));

            jsonObjectBuilder.add("entities", entitiesJsonObjectBuilder.build());

            entitiesIsPresent = true;
        }
        if (selectFields && !entitiesIsPresent && selectedFields.contains("hashtags")) {
            jsonObjectBuilder.add("hashtags",
                    produceHashtagsJsonArray(tweet.getEntities().getHashtags()));
        }
        if (selectFields && !entitiesIsPresent && selectedFields.contains("user_mentions")) {
            jsonObjectBuilder.add("user_mentions",
                    produceUserMentionsJsonArray(tweet.getEntities().getUserMentions()));
        }
        if (!selectFields || selectedFields.contains("coordinates")) {

            jsonObjectBuilder.add("coordinates", tweet.getCoordinates().isSet()
                            ? Json.createArrayBuilder()
                                  .add(tweet.getCoordinates().getLongitude())
                                  .add(tweet.getCoordinates().getLatitude())
                                  .build()
                            : JsonValue.NULL
                    );
        }
        if (!selectFields || selectedFields.contains("retweet_count"))
            jsonObjectBuilder.add("retweet_count", tweet.getRetweetCount());
        if (!selectFields || selectedFields.contains("favorite_count"))
            jsonObjectBuilder.add("favorite_count", tweet.getFavoriteCount());
        if (!selectFields || selectedFields.contains("favorited"))
            jsonObjectBuilder.add("favorited", tweet.isFavorited());
        if (!selectFields || selectedFields.contains("retweeted"))
            jsonObjectBuilder.add("retweeted", tweet.isRetweeted());


        prettyPrintJson(jsonObjectBuilder.build());
    }

    private static JsonArray produceHashtagsJsonArray(Hashtag[] hashtags) {

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Hashtag hashtag : hashtags) {

            JsonObjectBuilder jsonHashtagBuilder = Json.createObjectBuilder();
            jsonHashtagBuilder.add("indices",
                            Json.createArrayBuilder().add(hashtag.getIndex(0))
                                    .add(hashtag.getIndex(1)))
                                    .add("text", hashtag.getText());

            jsonArrayBuilder.add(jsonHashtagBuilder.build());
        }

        return jsonArrayBuilder.build();
    }

    private static JsonArray produceUserMentionsJsonArray(UserMention[] userMentions) {

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (UserMention userMention : userMentions) {

            JsonObjectBuilder jsonHashtagBuilder = Json.createObjectBuilder();
            jsonHashtagBuilder.add("screen_name", userMention.getScreenName())
                    .add("name", userMention.getName())
                    .add("id", userMention.getLongId())
                    .add("id_str", userMention.getStringId())
                    .add("indices",
                            Json.createArrayBuilder().add(userMention.getIndex(0))
                                    .add(userMention.getIndex(1)));

            jsonArrayBuilder.add(jsonHashtagBuilder.build());
        }

        return jsonArrayBuilder.build();
    }

    private static void prettyPrintJson(JsonObject jsonObject) {

        StringWriter writer = new StringWriter();
        HashMap<String, Object> map = new HashMap<>();
        map.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(map);
        JsonWriter jsonWriter = writerFactory.createWriter(writer);
        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();

        System.out.println(writer);
    }
}
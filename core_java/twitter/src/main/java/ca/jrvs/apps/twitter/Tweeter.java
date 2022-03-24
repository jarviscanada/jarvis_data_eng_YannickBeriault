package ca.jrvs.apps.twitter;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Tweeter {

    public void post(String tweetText, float[] latitudeLongitude) {

        TwitterDTO tweetToPost = new TwitterDTO(tweetText, latitudeLongitude);

        JsonArrayBuilder hashtagsArrayBuilder = Json.createArrayBuilder();
        for (String h : tweetToPost.getHashtags())
            hashtagsArrayBuilder.add(h);
        JsonArrayBuilder userMentionsArrayBuilder = Json.createArrayBuilder();
        for (String u : tweetToPost.getMentions())
            userMentionsArrayBuilder.add(u);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("created_at", tweetToPost.getCreatedAt().toString())
                .add("id", tweetToPost.getId())
                .add("id_str", Long.toString(tweetToPost.getId()))
                .add("text", tweetToPost.getText())
                .add("entities", Json.createObjectBuilder()
                        .add("hashtags", hashtagsArrayBuilder.build())
                        .add("user_mentions", userMentionsArrayBuilder.build())
                        .build())
                .add("coordinates", Json.createObjectBuilder()
                        .add("coordinates", Json.createArrayBuilder()
                                .add(tweetToPost.getLatitude())
                                .add(tweetToPost.getLongitude())
                                .build())
                        .add("type", "Point")
                        .build())
                .add("retweet_count", tweetToPost.getRetweetCount())
                .add("favorite_count", tweetToPost.getFavoriteCount())
                .add("favorited", tweetToPost.isFavorited())
                .add("retweeted", tweetToPost.isRetweeted())
                .build();

        printJsonObject(jsonObject);
    }

    private void printJsonObject(JsonObject jsonObject) {

        StringWriter prettyWriter = new StringWriter();

        Map<String, Object> map = new HashMap<>();
        map.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(map);
        JsonWriter jsonWriter = writerFactory.createWriter(prettyWriter);
        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();

        System.out.println(prettyWriter);
    }
}
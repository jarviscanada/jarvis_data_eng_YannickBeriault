package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.*;
import com.google.api.client.util.escape.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDAO implements CrdDao<Tweet, String> {

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String LOOKUP_PATH = "/1.1/statuses/lookup.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDAO(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet entity) {

        httpHelper = new TwitterHttpHelper();
        URI uri = producePostUri(entity);

        HttpResponse response = httpHelper.httpPost(uri);

        JsonObject jsonObject = parseResponse(response, HTTP_OK);
        String newId;
        try {
            newId = jsonObject.getString("id_str");
        } catch (NullPointerException e) {
            throw new RuntimeException("Unable to retrieve id from the server\'s response.");
        }

        entity.setId(newId);
        return entity;
    }

    private URI producePostUri(Tweet tweet) {

        PercentEscaper percentEscaper = new PercentEscaper("", false);
        String escapedTweetText = percentEscaper.escape(tweet.getText());

        try {
            return new URI(API_BASE_URI + POST_PATH
                    + QUERY_SYM + "status" + EQUAL + escapedTweetText);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to form URI.");
        }
    }

    private JsonObject parseResponse(HttpResponse response, int expectedStatusCode) {

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != expectedStatusCode) {

            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no body.");
            }
            throw new RuntimeException("Unexpected status code: " + statusCode);
        }

        if (response.getEntity() == null)
            throw new RuntimeException("Response has no body.");

        String responseString;
        try {
            responseString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to string.");
        }

        JsonObject responseJson = jsonStringToJsonObject(responseString);
        if (responseJson == null)
            throw new RuntimeException("Failed to convert response string to JSONObject.");

        return responseJson;
    }

    private JsonObject jsonStringToJsonObject(String jsonString) {

        char firstBracket = jsonString.charAt(0);
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));

        if (firstBracket == '[') {

            JsonArray jsonArray;

            try (jsonReader) {
                jsonArray = jsonReader.readArray();
            }

            return jsonArray.getJsonObject(0);
        } else if (firstBracket == '{') {

            try (jsonReader) {
                return jsonReader.readObject();
            }
        } else
            return null;
    }

    @Override
    public Tweet findById(String id) {

        httpHelper = getHttpHelper();
        URI uri = produceLookupUri(id);

        HttpResponse response = httpHelper.httpGet(uri);
        JsonObject jsonObject = parseResponse(response, HTTP_OK);

        return TweetUtil.createTweet(jsonObject);
    }

    private URI produceLookupUri(String id) {

        try {
            return new URI(API_BASE_URI + LOOKUP_PATH
                    + QUERY_SYM + "id" + EQUAL + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to form URI.");
        }
    }

    @Override
    public Tweet deleteById(String id) {

        Tweet tweetToReturn = findById(id);

        httpHelper = getHttpHelper();
        URI uri = produceDeleteUri(id);

        HttpResponse response = httpHelper.httpDelete(uri);
        parseResponse(response, HTTP_OK);

        return tweetToReturn;
    }

    private HttpHelper getHttpHelper() {
        return new TwitterHttpHelper();
    }

    private URI produceDeleteUri(String id) {

        try {
            return new URI(API_BASE_URI + DELETE_PATH
                    + "/" + id + ".json");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to form URI.");
        }
    }
}

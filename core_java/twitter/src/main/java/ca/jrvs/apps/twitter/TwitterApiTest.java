package ca.jrvs.apps.twitter;

import com.google.api.client.util.escape.PercentEscaper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

public class TwitterApiTest {

    private static String API_KEY = System.getenv("TWITTER_API_KEY");
    private static String API_SECRET = System.getenv("TWITTER_API_SECRET");
    private static String ACCESS_TOKEN = System.getenv("TWITTER_ACCESS_TOKEN");
    private static String ACCESS_SECRET = System.getenv("TWITTER_ACCESS_SECRET");

    public static void main(String[] args) throws OAuthMessageSignerException,
            OAuthExpectationFailedException, OAuthCommunicationException, IOException {

        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(API_KEY, API_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_SECRET);

        String status = "Hello World with API.";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        HttpPost request = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status="
                + percentEscaper.escape(status));

        consumer.sign(request);
        System.out.println("Http request headers:");
        Arrays.stream(request.getAllHeaders()).forEach(System.out::println);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
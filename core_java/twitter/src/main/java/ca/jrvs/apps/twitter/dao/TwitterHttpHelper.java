package ca.jrvs.apps.twitter.dao;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;

public class TwitterHttpHelper implements HttpHelper {

    private final OAuthConsumer consumer;
    private final HttpClient httpClient;

    public TwitterHttpHelper() {

        consumer = new CommonsHttpOAuthConsumer(System.getenv("TWITTER_API_KEY"),
                System.getenv("TWITTER_API_SECRET"));
        consumer.setTokenWithSecret(System.getenv("TWITTER_ACCESS_TOKEN"),
                System.getenv("TWITTER_ACCESS_SECRET"));

        httpClient = HttpClientBuilder.create().build();
    }

    public TwitterHttpHelper(String consumerKey, String consumerSecret,
                             String accessToken, String accessSecret) {

        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, accessSecret);

        httpClient = HttpClientBuilder.create().build();
    }

    public HttpResponse httpDelete(URI uri) {

        try {
            return communicateWithServer("DELETE", uri);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException
                | IOException | OAuthCommunicationException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HttpResponse httpPost(URI uri) {

        try {
            return communicateWithServer("POST", uri);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException
                | IOException | OAuthCommunicationException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public HttpResponse httpGet(URI uri) {

        try {
            return communicateWithServer("GET", uri);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException
                | IOException | OAuthCommunicationException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpResponse communicateWithServer(String method, URI uri) throws OAuthMessageSignerException,
            OAuthExpectationFailedException, OAuthCommunicationException, IOException {

        if (method.equals("GET") || method.equals("POST") || method.equals("DELETE")) {

            HttpPost request = new HttpPost(uri);
            consumer.sign(request);

            return httpClient.execute(request);
        } else
            throw new IllegalArgumentException("Http method " + method + " is not handled.");
    }
}
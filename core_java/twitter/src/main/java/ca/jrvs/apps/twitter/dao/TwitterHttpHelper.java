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

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret,
                             String accessToken, String tokenSecret) {

        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);

        httpClient = HttpClientBuilder.create().build();
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

        if (method.equals("GET") || method.equals("POST")) {

            HttpPost request = new HttpPost(uri);
            consumer.sign(request);

            return httpClient.execute(request);
        } else
            throw new IllegalArgumentException("Http method " + method + " is not handled.");
    }
}
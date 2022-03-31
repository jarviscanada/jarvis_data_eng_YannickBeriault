package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class TwitterHttpHelperTest {

    @Test
    public void testHttpGet() throws URISyntaxException {

        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(System.getenv("TWITTER_API_KEY"),
                System.getenv("TWITTER_API_SECRET"), System.getenv("TWITTER_ACCESS_TOKEN"),
                System.getenv("TWITTER_ACCESS_SECRET"));

        HttpResponse response = twitterHttpHelper.httpGet(new URI(
                "https://api.twitter.com/1.1/statuses/show.json?id=957682200954200068"));

        assertEquals(200, response.getStatusLine().getStatusCode());
    }
}

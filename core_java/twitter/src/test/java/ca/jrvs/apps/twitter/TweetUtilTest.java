package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TweetUtilTest {

    @Test
    public void testCreateTweetWithText() {

        Tweet nuTweet = TweetUtil.createTweet("mock-mock");
        assertEquals("mock-mock", nuTweet.getText());
    }

    @Test
    public void testCreateTweetWithIdAndText() {

        Tweet nuTweet = TweetUtil.createTweet("19681968", "Tweets are salty.");

        assertEquals("19681968", nuTweet.getIdString());
        assertEquals(19681968L, nuTweet.getId());
        assertEquals("Tweets are salty.", nuTweet.getText());
    }

    @Test
    public void testCreateTweetWithTextAndCoordinates() {

        Tweet nuTweet = TweetUtil.createTweet("Tweets are boring.", 25.8285f, 85.54f);

        assertEquals("Tweets are boring.", nuTweet.getText());
        assertEquals(25.8285f, nuTweet.getCoordinates().getLongitude(), 0.00001);
        assertEquals(85.54f, nuTweet.getCoordinates().getLatitude(), 0.001);
    }
}

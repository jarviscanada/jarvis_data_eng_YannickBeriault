package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.*;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwitterControllerIntegrationTest {

    private static Controller twitterController;
    private static String completeTweetStringId;
    private static String angryTweetId;
    private static String sweetHashtag = "#sweetTweets";
    private static String sweetUserMention = "@nenokia";
    private static String sweetText = "Tweets are sweets " + sweetHashtag + " " + sweetUserMention;

    private static String sweetCoordinatesString = "24.5615:60.1015";
    private static float sweetLatitude = 24.5615f;
    private static float sweetLongitude = 60.1015f;

    @BeforeClass
    public static void postCompleteTweet() {

        String post = "post";

        HttpHelper twitterHttpHelper = new TwitterHttpHelper();
        CrdDao<Tweet, String> twitterDAO = new TwitterDAO(twitterHttpHelper);
        Service twitterService = new TwitterService(twitterDAO);
        twitterController = new TwitterController(twitterService);

        Tweet returnedTweet = twitterController.postTweet(new String[] {post,
                sweetText, sweetCoordinatesString});
        completeTweetStringId = returnedTweet.getIdString();
    }

    @Test
    public void testShowEverythingValid() {

        Tweet retrievedTweet = twitterController.showTweet(new String[] {"show", completeTweetStringId});

        assertTrue(retrievedTweet.getCreatedAt() != null);
        assertTrue(retrievedTweet.getIdString() != null);
        assertEquals(sweetText, retrievedTweet.getText());
        assertEquals(sweetLongitude, retrievedTweet.getCoordinates().getLongitude(), 0.00001);
        assertEquals(sweetLatitude, retrievedTweet.getCoordinates().getLatitude(), 0.00001);
        assertEquals(sweetHashtag.substring(1), retrievedTweet.getEntities().getHashtags()[0].getText());
        assertEquals(35806417L, retrievedTweet.getEntities().getUserMentions()[0].getLongId());
    }

    @Test
    public void testShowFieldsValid() {

        String fields = "id,text";
        Tweet retrievedTweet = twitterController.showTweet(new String[] {"show",
                completeTweetStringId, fields});

        assertTrue(retrievedTweet.getIdString() != null);
        assertEquals(sweetText, retrievedTweet.getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShowFieldsException() {

        String fields = "id, trouble";
        twitterController.showTweet(new String[] {"show",
                completeTweetStringId, fields});
    }

    @Test
    public void testPostBasic() {

        String text = "Tweets are for getting angry, alone in front of your screen.";
        Tweet retrievedTweet = twitterController.postTweet(new String[] {"post", text});
        angryTweetId = retrievedTweet.getIdString();

        assertTrue(retrievedTweet.getCreatedAt() != null);
        assertTrue(retrievedTweet.getIdString() != null);
        assertEquals(text, retrievedTweet.getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostBasicExceptionTooLong() {
        twitterController.postTweet(new String[] {"post", TwitterServiceTest.TOO_LONG_FOR_A_TWEET});
    }

    @AfterClass
    public static void testDeletePosts() {

        String geekyTweetText = "One tweet a day means less time for coding.";
        String geekyTweetId = twitterController.postTweet(new String[] {"post", geekyTweetText})
                .getIdString();

        List<Tweet> deletedTweets = twitterController.deleteTweet(new String[] {"delete",
                completeTweetStringId + "," + angryTweetId + "," + geekyTweetId});

        for (Tweet tweet : deletedTweets) {

            assertTrue(tweet.getCreatedAt() != null);
            assertTrue(tweet.getIdString() != null);

            String exceptionMessage = "";
            try {
                twitterController.showTweet(new String[] {"show", tweet.getIdString()});
            } catch (TweetNotFoundException e) {
                exceptionMessage = e.getMessage();
            }

            assertEquals(TwitterDAO.TWEET_NOT_FOUND_EXCEPTION_MESSAGE, exceptionMessage);
        }

        assertEquals(3, deletedTweets.size());
    }
}
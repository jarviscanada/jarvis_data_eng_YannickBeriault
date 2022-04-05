package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TwitterDAOTest {

    @Test
    public void testCreateAndDelete() {

        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper();
        TwitterDAO twitterDAO = new TwitterDAO(twitterHttpHelper);

        Tweet tweetToCreate = new Tweet("Hello World through API");
        Tweet returnedTweet = twitterDAO.create(tweetToCreate);
        assertNotNull(returnedTweet.getIdString());

        returnedTweet = twitterDAO.deleteById(returnedTweet.getIdString());
        assertNotNull(returnedTweet.getIdString());
        assertNotNull(returnedTweet.getCreatedAt());
        assertNotNull(returnedTweet.getEntities());
    }

    @Test
    public void testFindById() {

        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper();
        TwitterDAO twitterDAO = new TwitterDAO(twitterHttpHelper);

        Tweet returnedTweet = twitterDAO.findById("963457776860192769");

        assertNotNull(returnedTweet.getCreatedAt());
        assertNotNull(returnedTweet.getIdString());
        assertNotNull(returnedTweet.getEntities());
    }
}

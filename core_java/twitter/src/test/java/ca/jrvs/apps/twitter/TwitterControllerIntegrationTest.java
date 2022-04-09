package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwitterControllerIntegrationTest {

    private Controller twitterController;
    private String completeTweetStringId;

    @BeforeClass
    public void postCompleteTweet() {

        String post = "post";
        String text = "Tweets are sweets.";
        String coordinates = "24.5615:60.1015";

        HttpHelper twitterHttpHelper = new TwitterHttpHelper();
        CrdDao<Tweet, String> twitterDAO = new TwitterDAO(twitterHttpHelper);
        Service twitterService = new TwitterService(twitterDAO);
        twitterController = new TwitterController(twitterService);

        Tweet returnedTweet = twitterController.postTweet(new String[] {post, text, coordinates});
        completeTweetStringId = returnedTweet.getIdString();
    }

    @Test
    public void test
}

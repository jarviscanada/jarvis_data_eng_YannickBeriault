package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetUtil;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceTest {

    private static final String TOO_LONG_FOR_A_TWEET = "This text is too long! You should know that a tweet needs to be " +
            "short... what were you thinking? Is this really important anyways? Go read a book, or code some" +
            " more...";
    private static final String[] VALID_TESTING_FIELDS_ARRAY = new String[] {"created_at", "coordinates",
            "id", "hashtags"};
    private static final String MOCK_ID = "5641984984684";

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService twitterService;

    @Test
    public void testPostTweetValid() {

        when(dao.create(any())).thenReturn(TweetUtil.createTweet("mocked"));

        assertNotNull(twitterService.postTweet(TweetUtil.createTweet("mocked")));
        assertNotNull(twitterService.postTweet(TweetUtil.createTweet("mocked", 73.3315f, 45.3032f)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostTweetExceptionLongText() {

        when(dao.create(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.postTweet(TweetUtil.createTweet(TOO_LONG_FOR_A_TWEET));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostTweetExceptionBadGeoTag1() {

        when(dao.create(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.postTweet(TweetUtil.createTweet("mocked", 200.58f, 45.3032f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostTweetExceptionBadGeoTag2() {

        when(dao.create(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.postTweet(TweetUtil.createTweet("mocked", 73.3315f, 95.25f));
    }

    @Test
    public void testShowTweetValid() {

        when(dao.findById(any())).thenReturn(TweetUtil.createTweet("mocked"));
        assertNotNull(twitterService.showTweet("685468541684", VALID_TESTING_FIELDS_ARRAY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShowTweetExceptionBadId1() {

        when(dao.findById(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.showTweet("-9468464889", VALID_TESTING_FIELDS_ARRAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShowTweetExceptionBadId2() {

        when(dao.findById(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.showTweet("9468464889651619861681968161681681", VALID_TESTING_FIELDS_ARRAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShowTweetExceptionBadId3() {

        when(dao.findById(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.showTweet("9468464889f", VALID_TESTING_FIELDS_ARRAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShowTweetExceptionBadField() {

        String[] invalidFields = VALID_TESTING_FIELDS_ARRAY;
        invalidFields[3] = "Social media amplifies hysteria.";

        when(dao.findById(any())).thenReturn(TweetUtil.createTweet("mocked"));
        twitterService.showTweet("9468464889", invalidFields);
    }

    @Test
    public void testDeleteTweets() {

        when(dao.deleteById(any())).thenReturn(TweetUtil.createTweet("mocked"));

        String[] mockIdsArray = new String[] {MOCK_ID, MOCK_ID, MOCK_ID, MOCK_ID};
        List<Tweet> returnedList = twitterService.deleteTweets(mockIdsArray);

        assertNotNull(returnedList);
        assertEquals(4, returnedList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteTweetsInvalidId() {

        when(dao.deleteById(any())).thenReturn(TweetUtil.createTweet("mocked"));

        String[] badIdsArray = new String[] {MOCK_ID, MOCK_ID, MOCK_ID, "-168198198"};
        twitterService.deleteTweets(badIdsArray);
    }
}

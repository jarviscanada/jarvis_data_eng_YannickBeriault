package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerTest {

    @Mock
    Service service;

    @InjectMocks
    TwitterController twitterController;

    @Test
    public void testPostTweetValid() {

        when(service.postTweet(any())).thenReturn(new Tweet("mocked"));
        assertNotNull(twitterController.postTweet(new String[] {"post", "mock text"}));
        assertNotNull(twitterController.postTweet(new String[] {"post", "mock text", "13.2418:52.3112"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostTweetExceptionNumberOfArgs() {

        when(service.postTweet(any())).thenReturn(new Tweet("mocked"));
        twitterController.postTweet(new String[] {"post", "mock text", "13.2418:52.3112", "too many"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostTweetExceptionNoFloatCoordinates() {

        when(service.postTweet(any())).thenReturn(new Tweet("mocked"));
        twitterController.postTweet(new String[] {"post", "mock text", "13.2418:523112ff"});
    }

    @Test
    public void testShowTweetValid() {

        when(service.showTweet(any(), any())).thenReturn(new Tweet("mocked"));
        twitterController.showTweet(new String[] {"show", "169819819", "id,text"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShowTweetExceptionNumberOfArgs() {

        when(service.showTweet(any(), any())).thenReturn(new Tweet("mocked"));
        twitterController.showTweet(new String[] {"show", "1981981", "id,text", "too many"});
    }

    @Test
    public void testDeleteTweetsValid() {

        Tweet mockedTweet = new Tweet("mocked");
        LinkedList<Tweet> mockedList = new LinkedList<>(Arrays.asList(mockedTweet, mockedTweet, mockedTweet));

        when(service.deleteTweets(any())).thenReturn(mockedList);
        List<Tweet> returnedList = twitterController.deleteTweet(new String[]
                {"delete", "169819819,116841984,1681491891"});
        assertNotNull(returnedList);
        assertEquals(3, returnedList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteTweetsExceptionNumberOfArgs() {

        Tweet mockedTweet = new Tweet("mocked");
        LinkedList<Tweet> mockedList = new LinkedList<>(Arrays.asList(mockedTweet, mockedTweet, mockedTweet));

        when(service.deleteTweets(any())).thenReturn(mockedList);
        twitterController.deleteTweet(new String[] {"delete", "169819819", "too many"});
    }
}

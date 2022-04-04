package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import com.google.api.client.util.escape.PercentEscaper;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TwitterHttpHelperTest {

    @Mock
    TwitterHttpHelper mockedHelper;

    @Test
    public void testHttpGet() throws URISyntaxException {

        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(System.getenv("TWITTER_API_KEY"),
                System.getenv("TWITTER_API_SECRET"), );

        HttpResponse response = twitterHttpHelper.httpGet(new URI(
                "https://api.twitter.com/1.1/statuses/lookup.json?id=966227360902189056"));

        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testHttpPost() throws URISyntaxException, IOException {

        TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper();

        String status = "Hello World with API.";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        HttpResponse response = twitterHttpHelper.httpPost(new URI(
                    "https://api.twitter.com/1.1/statuses/updat.json?status="
                        + percentEscaper.escape(status)));

        //Will not 200 because updat != update, in uri.
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}

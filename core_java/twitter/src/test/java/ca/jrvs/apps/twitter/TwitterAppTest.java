package ca.jrvs.apps.twitter;

import org.junit.Test;

import java.io.StringWriter;
import java.util.zip.DataFormatException;

import static org.junit.Assert.*;

public class TwitterAppTest {

    @Test
    public void testCheckArgsLength() {
        String[] threeArguments = {"CRUD", "This is not a test.", "34:87"};
        String[] twoArguments = {"show", "please"};
        String[] emptyStringArray = {};
        String[] fiveArguments = {"This", "should", "not", "work", "you know"};

        assertTrue(TwitterApp.checkArgsLength(threeArguments));
        assertTrue(TwitterApp.checkArgsLength(twoArguments));
        assertFalse(TwitterApp.checkArgsLength(emptyStringArray));
        assertFalse(TwitterApp.checkArgsLength(fiveArguments));
    }

    @Test
    public void testCheckOptionArgument() {

        assertTrue(TwitterApp.checkOptionArgument("Post"));
        assertTrue(TwitterApp.checkOptionArgument("poST"));
        assertTrue(TwitterApp.checkOptionArgument("show"));
        assertTrue(TwitterApp.checkOptionArgument("ShoW"));
        assertTrue(TwitterApp.checkOptionArgument("delete"));
        assertFalse(TwitterApp.checkOptionArgument("pose"));
        assertFalse(TwitterApp.checkOptionArgument("showw"));
        assertFalse(TwitterApp.checkOptionArgument("Delate"));
        assertFalse(TwitterApp.checkOptionArgument("Shaw"));
        assertFalse(TwitterApp.checkOptionArgument("Past"));
    }

    @Test(expected = DataFormatException.class)
    public void testGeoTagParserFalse1() throws DataFormatException {
        TwitterApp.geoTagParser("-100.25:235.88");
    }

    @Test(expected = DataFormatException.class)
    public void testGeoTagParserFalse2() throws DataFormatException {
        TwitterApp.geoTagParser("-100.25,235.88");
    }

    @Test(expected = DataFormatException.class)
    public void testGeoTagParserFalse3() throws DataFormatException {
        TwitterApp.geoTagParser("100.25:-235.88");
    }

    @Test(expected = DataFormatException.class)
    public void testGeoTagParserFalse4() throws DataFormatException {
        TwitterApp.geoTagParser("-100.25:235.88:25.74");
    }

    @Test
    public void testGeoTagParserTrue() throws DataFormatException {

        float epsilon = 0.00001f;
        float[] testArray1 = {-90.00f, 180.00f};
        float[] testArray2 = {-32.58f, 105.25f};
        float[] testArray3 = {89.78f, -178.25f};

        assertArrayEquals(testArray1, TwitterApp.geoTagParser("-90.00:180.00"), epsilon);
        assertArrayEquals(testArray2, TwitterApp.geoTagParser("-32.58:105.25"), epsilon);
        assertArrayEquals(testArray3, TwitterApp.geoTagParser("89.78:-178.25"), epsilon);
    }

    @Test
    public void testShowIdFormat() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        long id = twitterApp.tweeter.tweetBank.getAnyId();

        String[] arguments = {"show", Long.toString(id)};
        twitterApp.methodSwitch(arguments);
    }

    @Test(expected = DataFormatException.class)
    public void testShowIdFormatFalse() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();

        String[] arguments = {"show", "5419688ff"};
        twitterApp.methodSwitch(arguments);
    }

    @Test
    public void testShowMethodStringWriter() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        String[] emptyArray = new String[0];
        StringWriter writer = twitterApp.tweeter.show(496489496856468L, emptyArray);

        assertTrue(writer != null);
    }

    @Test
    public void testShowMethodOptionsTrue1() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        twitterApp.methodSwitch(new String[] {"show", "496489496856468", "created_at,text,retweet_count"});
    }

    @Test
    public void testShowMethodOptionsTrue2() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        twitterApp.methodSwitch(new String[] {"show", "496489496856468", "created_at,text,hashtags,retweet_count"});
    }

    @Test(expected = DataFormatException.class)
    public void testShowMethodOptionsFalse() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        twitterApp.methodSwitch(new String[] {"show", "496489496856468", "created_at,pendejada"});
    }

    @Test
    public void testDeleteMethod() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        twitterApp.methodSwitch(new String[] {"delete", "496489496856468,496489496856469"});

        assertTrue(twitterApp.tweeter.tweetBank.tweetBank.size() == 3);
    }

    @Test(expected = DataFormatException.class)
    public void testDeleteMethodFalse() throws DataFormatException {

        TwitterApp twitterApp = new TwitterApp();
        twitterApp.methodSwitch(new String[] {"delete", "496489496856467,496489496856469"});
    }
}
package ca.jrvs.apps.twitter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
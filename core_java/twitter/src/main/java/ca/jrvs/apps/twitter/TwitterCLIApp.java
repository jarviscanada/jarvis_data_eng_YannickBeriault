package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.TweetUtil;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TwitterCLIApp {

    public static final String USAGE_ERROR_MESSAGE = "Usage\n" +
            "TwitterApp post|show|delete \"tweet_text\" \"latitude:longitude\"\n" +
            "\nArguments:\n" +
            "tweet_text         - tweet_text cannot exceed 140 UTF-8 encoded characters. \n" +
            "latitude:longitude - Geo location.";

    private static Controller controller;

    @Autowired
    public TwitterCLIApp(Controller controller) {
        this.controller = controller;
    }

    public void run(String[] args) {

        if (args.length < 2)
            throw new IllegalArgumentException("More arguments are needed.\n" + USAGE_ERROR_MESSAGE);

        LinkedList<Tweet> returnedTweets = new LinkedList<>();

        if (args[0].equalsIgnoreCase("post"))
            returnedTweets.add(controller.postTweet(args));
        else if (args[0].equalsIgnoreCase("show"))
            returnedTweets.add(controller.showTweet(args));
        else if (args[0].equalsIgnoreCase("delete"))
            returnedTweets.addAll(controller.deleteTweet(args));
        else
            throw new IllegalArgumentException("This option/method is not available.");

        printTweets(returnedTweets);
    }

    private void printTweets(List<Tweet> tweetsToPrint) {

        for (Tweet tweet : tweetsToPrint) {

            TweetUtil.printTweet(tweet);
            System.out.println();
        }
    }
}

package ca.jrvs.apps.twitter;

import java.util.HashMap;
import java.util.Set;

public class TweetBankMock {

    HashMap<Long, TwitterDTO> tweetBank;

    public TweetBankMock() {

        this.tweetBank = new HashMap<>();

        TwitterDTO tweet1 = new TwitterDTO("This is not a tweet.", new float[]{41.00f, 28.5718f});
        TwitterDTO tweet2 = new TwitterDTO("The world is a vampire.", new float[]{55.4521f, 37.372f});
        TwitterDTO tweet3 = new TwitterDTO("My gigaloo is spinning.", new float[]{17.59f, 94.33f});
        TwitterDTO tweet4 = new TwitterDTO();
        this.tweetBank.put(tweet1.getId(), tweet1);
        this.tweetBank.put(tweet2.getId(), tweet2);
        this.tweetBank.put(tweet3.getId(), tweet3);
        this.tweetBank.put(tweet4.getId(), tweet4);
    }

    public TwitterDTO getTweet(long id) {
        return this.tweetBank.getOrDefault(id, null);
    }

    public long getAnyId() {

        Set<Long> keySet = this.tweetBank.keySet();
        return keySet.iterator().next();
    }

    public void put(TwitterDTO tweet) {
        this.tweetBank.put(tweet.getId(), tweet);
    }
}

package ca.jrvs.apps.twitter.model;

public class Hashtag {

    private int[] indices;
    private String text;

    public Hashtag(int[] indices, String text) {

        this.indices = indices;
        this.text = text;
    }
}

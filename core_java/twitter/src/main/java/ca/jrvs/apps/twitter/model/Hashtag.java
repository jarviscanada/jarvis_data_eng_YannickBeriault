package ca.jrvs.apps.twitter.model;

public class Hashtag {

    private int[] indices;
    private String text;

    public Hashtag(int[] indices, String text) {

        this.indices = indices;
        this.text = text;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getIndex(int i) {
        return indices[i];
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

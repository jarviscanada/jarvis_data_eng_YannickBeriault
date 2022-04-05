package ca.jrvs.apps.twitter.model;

public class UserMention {

    private ID id;
    private String name;
    private int[] indices;
    private String screenName;

    public UserMention(String id, String name, String screenName) {

        this.id = new ID(id);
        this.name = name;
        this.screenName = screenName;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }
}

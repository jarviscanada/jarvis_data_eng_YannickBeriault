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

    public int getIndex(int i) {
        return indices[i];
    }

    public long getLongId() {
        return id.getLongId();
    }

    public String getStringId() {
        return id.getIdString();
    }

    public String getName() {
        return name;
    }

    public int[] getIndices() {
        return indices;
    }

    public String getScreenName() {
        return screenName;
    }
}

package ca.jrvs.apps.twitter.model;

public class ID {

    private final long longId;
    private final String idString;

    public ID(long id) {

        this.longId = id;
        this.idString = Long.toString(id);
    }

    public ID(String idString) {

        this.longId = Long.parseLong(idString);
        this.idString = idString;
    }

    public long getLongId() {
        return longId;
    }

    public String getIdString() {
        return idString;
    }
}

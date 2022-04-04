package ca.jrvs.apps.twitter.model;

public class Coordinates {

    private float longitude;
    private float latitude;
    private String type;

    public Coordinates(float longitude, float latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
        this.type = "Point";
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float[] getCoordinatesArray() {

        return new float[]{longitude, latitude};
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

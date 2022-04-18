package ca.jrvs.apps.twitter.model;

public class Coordinates {

    private float longitude;
    private float latitude;
    private boolean isSet = false;

    public Coordinates() {}

    public Coordinates(float longitude, float latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
        isSet = true;
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
        isSet = true;
    }

    public void setLatitude(float latitude) {

        this.latitude = latitude;
        isSet = true;
    }

    public boolean isSet() {
        return isSet;
    }
}

package ca.jrvs.apps.trading.dao;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(RuntimeException e) {
        super(e);
    }
}
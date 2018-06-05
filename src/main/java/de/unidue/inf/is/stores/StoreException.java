package de.unidue.inf.is.stores;

public final class StoreException extends RuntimeException {

    private static final long serialVersionUID = -1626236348481345515L;


    public StoreException(Exception e) {
        super(e);
    }

}

package Exceptions;

/**
 * Created by nareddyt on 1/15/16.
 */
public class InvalidDeckNumberException extends Exception {
    public InvalidDeckNumberException(String data) {
        super(data);
    }
}

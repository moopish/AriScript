package ari.misc;

/**
 * Created by Michael on 09/10/2015.
 *
 */
public class InstantiationException extends RuntimeException {
    public InstantiationException(Class c) {
        super("cannot instantiate " + c.getName());
    }
}

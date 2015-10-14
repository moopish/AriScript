package ari.lang;

/**
 * Created by Michael on 09/10/2015.
 *
 */
public abstract class Expression {
    public abstract Expression evaluate();
    public abstract Expression add(Expression expression);

    public static final class CannotAddException extends RuntimeException {
        
    }

    public abstract String parsedForm();
}

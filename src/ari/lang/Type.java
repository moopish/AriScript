package ari.lang;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public abstract class Type extends Expression {
    public abstract String type();

    @Override
    public Expression add(Expression expression) {
        throw new CannotAddException();
    }

    @Override
    public Expression evaluate() {
        return (this);
    }
}

package ari.lang;

/**
 * Created by Michael on 09/10/2015.
 *
 */
public class Program extends Expression {

    private Expression entry_point;

    public Program(Expression entry_point) {
        this.entry_point = entry_point;
    }

    @Override
    public String parsedForm() {
        return ("program " + entry_point.parsedForm());
    }

    @Override
    public Expression evaluate() {
        return (entry_point.evaluate());
    }

    @Override
    public Expression add(Expression expression) {
        if (entry_point == null)
            entry_point = expression;
        else
            throw new CannotAddException();
        return (this);
    }
}

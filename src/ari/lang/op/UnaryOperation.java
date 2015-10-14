package ari.lang.op;

import ari.lang.Expression;
import ari.lang.op.Operation;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public abstract class UnaryOperation extends Operation {

    private Expression operand;

    protected UnaryOperation(Operator operator, Expression operand) {
        super(operator);
        this.operand = operand;
    }

    @Override
    public Expression add(Expression expression) {
        if (operand == null)
            operand = expression;
        else
            throw new CannotAddException();
        return (this);
    }

    @Override
    public String parsedForm() {
        return (operator().symbol() + "(" + operand.parsedForm() + ")");
    }

    protected Expression getOperand() {
        return (operand);
    }

    public final static class UnsupportedUnaryOperationException extends Operation.UnsupportedOperationException {
        public UnsupportedUnaryOperationException(Expression exp, Operator op) {
            super(exp.getClass().getName() + " does not support " + op.symbol());
        }
    }
}

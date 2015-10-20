package puel.lang.op;

import puel.lang.Expression;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public abstract class BinaryOperation extends Operation {

    private Expression left;
    private Expression right;

    protected BinaryOperation(Operator operator, Expression left, Expression right) {
        super(operator);
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression add(Expression expression) {
        if (left == null)
            left = expression;
        else if (right == null)
            right = expression;
        else
            throw new CannotAddException();
        return (this);
    }

    @Override
    public String parsedForm() {
        return ("(" + left.parsedForm() + operator().symbol() + right.parsedForm() + ")");
    }

    protected Expression getLeftOperand() {
        return (left);
    }

    protected Expression getRightOperand() {
        return (right);
    }

    public static BinaryOperation newBinOp(Operator op, Expression left, Expression right) {
        switch (op) {
            case AND:
                return (new LogicalAnd(left, right));
            case OR:
                return (new LogicalOr(left, right));
            case EQUIV:
                return (new Equivalence(left, right));
            case NONEQ:
                return (new NonEquivalence(left, right));
            default:
                throw new UnsupportedBinaryOperationException(left, right, op);
        }
    }

    public final static class UnsupportedBinaryOperationException extends Operation.UnsupportedOperationException {
        public UnsupportedBinaryOperationException(Expression left, Expression right, Operator op) {
            super(left.getClass().getName() + " does not support " + op.symbol() + " with " + right.getClass().getName());
        }
    }
}

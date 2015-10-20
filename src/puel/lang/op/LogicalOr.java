package puel.lang.op;

import puel.lang.*;
import puel.lang.Boolean;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public final class LogicalOr extends BinaryOperation {

    public LogicalOr(Expression left, Expression right) {
        super(Operator.OR, left, right);
    }

    @Override
    public Expression evaluate() {
        Expression left = getLeftOperand().evaluate();
        Expression right = getRightOperand().evaluate();

        if (left instanceof Boolean && right instanceof Boolean) {
            return (((Boolean) left).or((Boolean) right));
        } else {
            throw new UnsupportedBinaryOperationException(left, right, operator());
        }
    }
}

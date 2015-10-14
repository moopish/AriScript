package ari.lang.op;

import ari.lang.*;
import ari.lang.Boolean;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public final class LogicalAnd extends BinaryOperation {

    public LogicalAnd(Expression left, Expression right) {
        super(Operator.AND, left, right);
    }

    @Override
    public Expression evaluate() {
        Expression left = getLeftOperand().evaluate();
        Expression right = getRightOperand().evaluate();

        if (left instanceof Boolean && right instanceof Boolean) {
            return (((Boolean) left).and((Boolean) right));
        } else {
            throw new UnsupportedBinaryOperationException(left, right, operator());
        }
    }
}

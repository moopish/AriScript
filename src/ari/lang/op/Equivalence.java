package ari.lang.op;

import ari.lang.*;
import ari.lang.Boolean;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public final class Equivalence extends BinaryOperation {

    public Equivalence(Expression left, Expression right) {
        super(Operator.EQUIV, left, right);
    }

    @Override
    public Expression evaluate() {
        Expression left = getLeftOperand().evaluate();
        Expression right = getRightOperand().evaluate();

        if (left instanceof Boolean && right instanceof Boolean) {
            return (((Boolean) left).equiv((Boolean) right));
        } else {
            throw new BinaryOperation.UnsupportedBinaryOperationException(left, right, operator());
        }
    }
}

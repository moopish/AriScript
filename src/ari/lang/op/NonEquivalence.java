package ari.lang.op;

import ari.lang.Boolean;
import ari.lang.Expression;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public final class NonEquivalence extends BinaryOperation {

    public NonEquivalence(Expression left, Expression right) {
        super(Operator.NONEQ, left, right);
    }

    @Override
    public Expression evaluate() {
        Expression left = getLeftOperand().evaluate();
        Expression right = getRightOperand().evaluate();

        if (left instanceof Boolean && right instanceof Boolean) {
            return (((Boolean) left).noneq((Boolean) right));
        } else {
            throw new BinaryOperation.UnsupportedBinaryOperationException(left, right, operator());
        }
    }
}

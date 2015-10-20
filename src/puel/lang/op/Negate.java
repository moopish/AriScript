package puel.lang.op;

import puel.lang.*;
import puel.lang.Boolean;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public final class Negate extends UnaryOperation {

    public Negate(Expression operand) {
        super(Operator.NOT, operand);
    }

    @Override
    public Expression evaluate() {
        Expression operand = getOperand().evaluate();

        if (operand instanceof puel.lang.Boolean) {
            return (((Boolean) operand).not());
        } else {
            throw new UnsupportedUnaryOperationException(operand, operator());
        }
    }
}

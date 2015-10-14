package ari.lang.op;

import ari.lang.Expression;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public abstract class Operation extends Expression {

    private Operator operator;

    protected Operation(Operator operator) {
        this.operator = operator;
    }

    public Operator operator() {
        return (operator);
    }

    public enum Operator {
        NOT("!", 2),
        MULT("*", 3),
        DIV("/", 3),
        ADD("+", 4),
        SUB("-", 4),
        EQUIV("==", 7),
        NONEQ("!=", 7),
        AND("&&", 11),
        OR("||", 12),
        NONE("", Integer.MAX_VALUE);

        private final int prec;
        private final String sym;

        Operator(String sym, int prec) {
            this.sym = sym;
            this.prec = prec;
        }

        public int precedence() {
            return (prec);
        }

        public String symbol() {
            return (sym);
        }
    }

    protected static class UnsupportedOperationException extends RuntimeException {
        protected UnsupportedOperationException(String message) {
            super(message);
        }
    }
}

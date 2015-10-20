package puel.lang.env;

import puel.misc.Dumpable;
import puel.misc.Util;

/**
 * Created by Michael on 14/10/2015.
 *
 */
public final class Function extends Defined implements Dumpable {

    private final static Util.StringChecker FUNC_CHECK = new Util.StringChecker() {
        @Override
        public boolean check(char c) {
            return (Util.STR_ALPHANUMERIC.check(c) || c == '_' || c == '\'');
        }
    };

    private final DataType return_type;
    private final String[] param_names;
    private final DataType[] param_types;

    public Function(String name, String namespace, DataType rtype, String[] pnames, DataType[] ptypes) {
        super(name, namespace);
        this.return_type = rtype;
        this.param_names = pnames;
        this.param_types = ptypes;
    }

    public static boolean validFunctionID(String name) {
        return(Util.stringCheck(name, FUNC_CHECK));
    }

    @Override
    public String dump(int level) {
        return null;
    }

    @Override
    public String subdump() {
        return null;
    }

    public static final class InvalidFunctionIDException extends FunctionException {
        public InvalidFunctionIDException(String name) {
            super("invalid name for a Function : \'" + name + "\'\nmust be alphanum, _ or ' characters only");
        }
    }

    public static abstract class FunctionException extends RuntimeException {
        public FunctionException(String message) {
            super(message);
        }
    }

    public static final class InvalidFunctionDeclarationException extends FunctionException {
        public InvalidFunctionDeclarationException(String message) {
            super("invalid function declaration: " + message);
        }
    }
}

package ari.misc;

/**
 * Created by Michael on 12/10/2015.
 *
 */
public final class Util {

    public static final StringChecker STR_ALPHA = new StringChecker() {
        @Override
        public boolean check(char c) {
            return ('a' <= c && 'z' >= c || 'A' <= c && 'Z' >= c);
        }
    };

    public static final StringChecker STR_NUMERIC = new StringChecker() {
        @Override
        public boolean check(char c) {
            return ('0' <= c && '9' >= c);
        }
    };

    public static final StringChecker STR_ALPHANUMERIC = new StringChecker() {
        @Override
        public boolean check(char c) {
            return (STR_ALPHA.check(c) && STR_NUMERIC.check(c));
        }
    };

    private Util() {
        throw new InstantiationException(Util.class);
    }

    public static boolean isAlpha(String str) {
        return (stringCheck(str, STR_ALPHA));
    }
    public static boolean isNumeric(String str) {
        return (stringCheck(str, STR_NUMERIC));
    }
    public static boolean isAlphaNumeric(String str) {
        return (stringCheck(str, STR_ALPHANUMERIC));
    }

    public static boolean stringCheck(String str, StringChecker strchk) {
        for (int i=0; i<str.length(); ++i) {
            if (!strchk.check(str.charAt(i))) {
                return (false);
            }
        }
        return (true);
    }

    public interface StringChecker {
        boolean check(char c);
    }
}

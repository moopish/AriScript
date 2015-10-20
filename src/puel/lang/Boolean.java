package puel.lang;

/**
 * Created by Michael on 09/10/2015.
 *
 */
public final class Boolean extends PrimitiveType {
    private boolean value;

    public Boolean(boolean value) {
        this.value = value;
    }

    @Override
    public String type() {
        return ("bool");
    }

    @Override
    public String parsedForm() {
        return ("(" + value + ")");
    }


    public Boolean and(Boolean other) {
        return (new Boolean(value && other.value));
    }

    public Boolean equiv(Boolean other) {
        return (new Boolean(value == other.value));
    }

    public Boolean noneq(Boolean other) {
        return (new Boolean(value != other.value));
    }

    public Boolean not() {
        return (new Boolean(!value));
    }

    public Boolean or(Boolean other) {
        return (new Boolean(value || other.value));
    }

}

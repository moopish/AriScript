package ari.lang;

/**
 * Created by Michael on 10/10/2015.
 *
 */
public final class Integer extends PrimitiveType {

    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public String type() {
        return ("int");
    }

    @Override
    public String parsedForm() {
        return ("(" + value + ")");
    }

    public Integer add(Integer other) {
        return (new Integer(value + other.value));
    }

    public Integer sub(Integer other) {
        return (new Integer(value - other.value));
    }

    public Integer mult(Integer other) {
        return (new Integer(value * other.value));
    }

    public Integer div(Integer other) {
        return (new Integer(value / other.value));
    }
}

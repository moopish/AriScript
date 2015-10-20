package puel.lang.env;

import puel.misc.Dumpable;

/**
 * <p><tt>Defined</tt> - Oct. 14, 2015</p>
 *
 * <p>This class represents a defined 'presence' in the environment.
 * They must have a unique name and namespace pair, meaning no two
 * defined, of the same type, can have both the same name and
 * namespace. Two defined of different types can have the same name
 * and namespace, as needed for a {@link DataType} and its related
 * {@link Namespace}.</p>
 *
 * @see Environment
 *
 * @author Michael van Dyk
 */
public abstract class Defined implements Dumpable {

    private final String    name;       // The name of the Defined
    private final String    namespace;  // The namespace of the Defined

    /**
     * Sets up the <tt>Defined</tt>'s name and namespace
     * @param name      the name of the <tt>Defined</tt>
     * @param namespace the namespace of the <tt>Defined</tt>
     */
    protected Defined(String name, String namespace) {
        this.name = name;
        this.namespace = namespace;
    }

    /**
     * Gets the full name of the <tt>Defined</tt>. That is the
     * name with the leading namespace appended to it.
     * @return the full name of the <tt>Defined</tt>
     */
    public final String fullname() {
        if (namespace.equals(Namespace.EMPTY_NAMESPACE))
            return (name);
        return (namespace + "." + name);
    }

    /**
     * The name of the <tt>Defined</tt>
     * @return the name
     */
    public final String name() {
        return (name);
    }

    /**
     * The namespace of the <tt>Defined</tt>
     * @return the namespace
     */
    public final String namespace() {
        return (namespace);
    }

    /**
     * @return the dump(0) of the <tt>Defined</tt>
     */
    @Override
    public final String toString() {
        return (dump(0));
    }
}

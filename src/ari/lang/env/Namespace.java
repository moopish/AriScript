package ari.lang.env;

import ari.misc.Dumpable;
import ari.misc.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p><tt>Namespace</tt> - Oct. 12, 2015</p>
 *
 * <p>The <tt>Namespace</tt> class simply makes an easy organization
 * of 'presences' that are within the environment. The class
 * controls the <tt>DataType</tt>s, <tt>Function</tt>s and
 * <tt>Namespace</tt>s defined for a namespace.</p>
 *
 * @see Defined
 * @see Dumpable
 * @see Environment
 *
 * @author Michael van Dyk
 */
public final class Namespace extends Defined implements Dumpable {

    /**
     *   The name of the main subspace, namespace is removed from
     * path as only this namespace is the only one at the top level.
     */
    public static final String EMPTY_NAMESPACE = "ari";

    private final Map<String, Namespace>    subspaces;  // The subspaces of this current namespace
    private final Map<String, DataType>     types;      // The types define in this namespace

    /**
     * Defines a new namespace
     * @param name      name of the namespace
     * @param namespace the path to the space (namespace of the namespace)
     */
    public Namespace(String name, String namespace) {
        super(name, namespace);
        types = new HashMap<String, DataType>();
        subspaces = new HashMap<String, Namespace>();
    }

    /**
     * Defines a new type in the specified namespace.
     * @param name the name of namespace to add to
     * @param path the namespace of the namespace
     * @param type the type to add
     */
    protected void addType(String name, String path, DataType type) {
        if (!Util.isAlpha(name)) {
            throw new DataType.InvalidTypeIDException(name);
        }

        Namespace add_to = path.equals(EMPTY_NAMESPACE) ? this : getSubspace(path);

        if (add_to.types.containsKey(name)) {
            //TODO error
        }

        add_to.types.put(name, type);
    }

    /**
     * Checks of the type exists given the fullname of the type.
     * @see Defined#fullname()
     * @param fullname the fullname of the type
     * @return true if the type does exist, false otherwise
     */
    protected boolean hasType(String fullname) {
        return (getType(fullname) != null);
    }

    /**
     * Gets the type with the given fullname.
     * @see Defined#fullname()
     * @param fullname the fullname of the type
     * @return the type if exists, null if not
     */
    protected DataType getType(String fullname) {
        int last = fullname.lastIndexOf('.');

        if (last == -1) {
            return (types.get(fullname));
        } else {
            String path = fullname.substring(0, last);
            String type = fullname.substring(last + 1);

            return (getSubspace(path).types.get(type));
        }
    }

    protected void newSubspace(String subspace, String namespace) {
        if (!Util.isAlpha(subspace)) {
            throw new Namespace.InvalidNamespaceIDException(subspace);
        }

        Namespace curr;
        if (!namespace.equals(Namespace.EMPTY_NAMESPACE)) {
            curr = getSubspace(namespace);
        } else {
            curr = this;
        }
        if (!curr.subspaces.containsKey(subspace))
            curr.subspaces.put(subspace, new Namespace(subspace, namespace));
    }

    protected Namespace getSubspace(String subspace) {
        if (!Namespace.validNamespaceChain(subspace)) {
            throw new Namespace.InvalidNamespaceIDException(subspace);
        }

        int index;
        if ((index = subspace.indexOf('.')) != -1) {
            String lead = subspace.substring(0, index);

            if (lead.equals(EMPTY_NAMESPACE))
                return getSubspace(subspace.substring(index + 1));
            return (subspaces.get(lead).getSubspace(subspace.substring(index + 1)));
        } else {
            return (subspace.equals(EMPTY_NAMESPACE) ? this : subspaces.get(subspace));
        }
    }

    @Override
    public String dump(int level) {
        String tab = "";

        for (int i=0; i<level; ++i) {
            tab += DUMP_TAB;
        }

        String dump = tab + "#" + name() + ":";
        String contents = "";

        for (DataType d : types.values()) {
            contents += "\n" + d.dump(level + 1);
        }

        if (!contents.equals(""))
            contents += "\n";

        for (Namespace n : subspaces.values()) {
            contents += "\n" + n.dump(level + 1) + "\n";
        }

        dump += ((!contents.equals("")) ? contents + tab : "") + ";";

        return (dump);
    }

    @Override
    public String subdump() {
        String ret = "";

        for (Namespace n : subspaces.values())
            ret += n.dump(0) + "\n\n";

        return (ret);
    }

    /**
     * Checks if the given string is a valid namespace chain,
     * meaning that could the given string represent a namespace
     * and its location in the namespace hierarchy.
     *
     * @param namespace the given string to check
     * @return true if the string is valid, false otherwise
     */
    public static boolean validNamespaceChain(String namespace) {
        boolean last_alpha = false;

        for (int i=0; i<namespace.length(); ++i) {
            if (namespace.charAt(i) == '.') {
                if (!last_alpha)
                    return (false);
                else
                    last_alpha = false;
            } else if (Util.STR_ALPHA.check(namespace.charAt(i))) {
                last_alpha = true;
            } else {
                return (false);
            }
        }

        return (last_alpha);
    }

    public static final class InvalidNamespaceIDException extends NamespaceException {
        public InvalidNamespaceIDException(String name) {
            super("invalid name for a namespace : \'" + name + "\'\nmust be alpha characters only");
        }
    }

    public static abstract class NamespaceException extends RuntimeException {
        public NamespaceException(String message) {
            super(message);
        }
    }

    public static final class InvalidNamespaceDeclarationException extends NamespaceException {
        public InvalidNamespaceDeclarationException(String message) {
            super("invalid namespace declaration: " + message);
        }
    }
}

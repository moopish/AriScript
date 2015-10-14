package ari.lang.env;

import ari.misc.Dumpable;
import ari.misc.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 12/10/2015.
 *
 */
public final class Namespace implements Dumpable {

    public static final String EMPTY_NAMESPACE = "";

    private final String name;

    private final Map<String, Namespace> subspaces;
    private final Map<String, DefinedType> types;

    public Namespace(String name) {
        this.name = name;
        types = new HashMap<String, DefinedType>();
        subspaces = new HashMap<String, Namespace>();
    }

    protected  void addType(String name, DefinedType type) {
        types.put(name, type);
    }

    protected boolean hasType(String name) {
        return (types.containsKey(name));
    }

    protected DefinedType getType(String name) {
        return (types.get(name));
    }

    protected void newSubspace(String subspace) {
        if (!subspaces.containsKey(subspace))
            subspaces.put(subspace, new Namespace(subspace));
    }

    protected Namespace getSubspace(String subspace) {
        return (subspaces.get(subspace));
    }

    @Override
    public String dump(int level) {
        String tab = "";

        for (int i=0; i<level; ++i) {
            tab += DUMP_TAB;
        }

        String dump = tab + "#" + name + ":";
        String contents = "";

        for (DefinedType d : types.values()) {
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

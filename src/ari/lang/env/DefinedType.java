package ari.lang.env;

import ari.misc.Dumpable;

/**
 * Created by Michael on 12/10/2015.
 *
 */
public final class DefinedType implements Dumpable {

    private final String name;
    private final String namespace;
    private final String[] attribute_ids;
    private final DefinedType[] attribute_types;

    public DefinedType(String name, String namespace, String[] ids, DefinedType[] types) {
        this.name = name;
        this.namespace = namespace;
        this.attribute_ids = ids;
        this.attribute_types = types;
    }

    public String getFullName() {
        if (!namespace.equals(""))
            return (namespace + "." + name);
        return (name);
    }

    @Override
    public String dump(int level) {
        String tab = "";

        for (int i=0; i<level; ++i) {
            tab += DUMP_TAB;
        }

        String dump = tab + "~" + name + ":";
        String contents = "";

        for (int i=0; i< attribute_ids.length; ++i) {
            contents += " " + attribute_ids[i] + "|" + attribute_types[i].getFullName();
            if (i != attribute_ids.length - 1)
                contents += ",";
        }

        dump += contents + ";";

        return (dump);
    }

    public static abstract class DefinedTypeException extends RuntimeException {
        public DefinedTypeException(String message) {
            super(message);
        }
    }

    public static final class InvalidTypeIDException extends DefinedTypeException {
        public InvalidTypeIDException(String name) {
            super("invalid name for a type : \'" + name + "\'\nmust be alpha characters only");
        }
    }

    public static final class InvalidTypeDeclarationException extends DefinedTypeException {
        public InvalidTypeDeclarationException(String message) {
            super("invalid type declaration: " + message);
        }
    }
}

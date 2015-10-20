package puel.lang.env;

import puel.misc.Dumpable;

/**
 * Created by Michael on 12/10/2015.
 *
 */
public final class DataType extends Defined implements Dumpable {

    private final String[] attribute_ids;
    private final DataType[] attribute_types;

    public DataType(String name, String namespace, String[] ids, DataType[] types) {
        super(name, namespace);
        this.attribute_ids = ids;
        this.attribute_types = types;
    }

    @Override
    public String dump(int level) {
        String tab = "";

        for (int i=0; i<level; ++i) {
            tab += DUMP_TAB;
        }

        String dump = tab + "~" + name() + ":";
        String contents = "";

        for (int i=0; i< attribute_ids.length; ++i) {
            contents += " " + attribute_ids[i] + "|" + attribute_types[i].fullname();
            if (i != attribute_ids.length - 1)
                contents += ",";
        }

        dump += contents + ";";

        return (dump);
    }

    @Override
    public String subdump() {
        String ret = "";
        for (DataType dt : attribute_types)
            ret += dt.dump(0) + "\n\n";
        return (ret);
    }

    public static abstract class DataTypeException extends RuntimeException {
        public DataTypeException(String message) {
            super(message);
        }
    }

    public static final class InvalidTypeIDException extends DataTypeException {
        public InvalidTypeIDException(String name) {
            super("invalid name for a type : \'" + name + "\'\nmust be alpha characters only");
        }
    }

    public static final class InvalidTypeDeclarationException extends DataTypeException {
        public InvalidTypeDeclarationException(String message) {
            super("invalid type declaration: " + message);
        }
    }
}
